package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

import java.util.ArrayList;
import java.util.List;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private final DataSource dataSource;
    private final String SelectMessage = "SELECT * FROM Message WHERE id = ?";
    private final String SelectUser = "SELECT * FROM Users WHERE id = ?";
    private final String SelectChat = "SELECT * FROM Chatroom WHERE id = ?";

    public MessagesRepositoryJdbcImpl ( DataSource dataSource ) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById ( Long id ) throws SQLException {
        Optional<Message> optionalMessage = Optional.empty();

            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SelectMessage);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()) return null;

            User user = FindUser(resultSet.getLong(2));
            Chatroom room = FindChatroom(resultSet.getLong(3));
            optionalMessage = Optional.of(new Message(resultSet.getLong(1), user, room, resultSet.getString(4) , LocalDateTime.now()));
            
            System.out.println(optionalMessage);
        return optionalMessage;
    }

    @Override
    public void save(Message message) throws Exception {
        Long userId, roomId;
        String localDateTime = "'null'";

        userId = message.GetAuthor().GetId();
        roomId = message.GetRoom().GetId();

        Connection connect = dataSource.getConnection();
        PreparedStatement statement = connect.prepareStatement(SelectUser);
        statement.setLong(1, userId);
        ResultSet res = statement.executeQuery();

        if (!res.next()) throw new NotSavedSubEntityException("User with id = " + userId + " doesn't exist");

        statement = connect.prepareStatement(SelectChat);
        statement.setLong(1, roomId);
        res = statement.executeQuery();
        
        if (!res.next()) throw new NotSavedSubEntityException("Chatroom with id = " + roomId + " doesn't exist");
        
        if (message.GetDateTime() != null) localDateTime = "'" + Timestamp.valueOf(message.GetDateTime()) + "'";
        
        statement = connect.prepareStatement("INSERT INTO Message (id, author_id, room_id, text, time) VALUES (?, ?, ?, ?, ?) RETURNING id");
        statement.setLong(1, message.GetId());
        statement.setLong(2, userId);
        statement.setLong(3, roomId);
        statement.setString(4, message.GetText());
        statement.setTimestamp(5, Timestamp.valueOf(message.GetDateTime()));
        res = statement.executeQuery();

        if (!res.next()) throw new NotSavedSubEntityException("Internal Error");
        message.SetId(res.getLong(1));
    }

    @Override
    public void update(Message message) throws Exception {
        Timestamp localDateTime = null;
        System.out.println("Updating message " + message.GetId());
        
        Connection connect = dataSource.getConnection();
        PreparedStatement statement = connect.prepareStatement(SelectMessage);
        statement.setLong(1, message.GetId());
        ResultSet res = statement.executeQuery();
    
        if (!res.next()) {
            save(message);
            return;
        }
        
        if (message.GetText() == null) message.SetText("");
        if (message.GetDateTime() != null) localDateTime = Timestamp.valueOf(message.GetDateTime());
        statement = connect.prepareStatement("UPDATE Message SET author_id = ?, room_id = ?, text = ?, time = ? WHERE id = ?");
        statement.setLong(1, message.GetAuthor().GetId());
        statement.setLong(2, message.GetRoom().GetId());
        statement.setString(3, message.GetText());
        statement.setTimestamp(4, localDateTime);
        statement.setLong(5, message.GetId());
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public List<User> findAll(int page, int size) throws SQLException {
        List<User> users = new ArrayList<>();
        
        String sql = "WITH cte AS (SELECT users.id, login, password, owner, room_id  FROM (users JOIN message ON users.id = author_id) JOIN chatroom ON chatroom.id = room_id) SELECT * FROM cte WHERE id BETWEEN ? AND ?;";
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, page * size);
        statement.setLong(2, page * size + size);
        ResultSet resultSet = statement.executeQuery();

        for (;resultSet.next();) {
            List<Chatroom> created_rooms = new ArrayList<>();
            List<Chatroom> use_chatroom = new ArrayList<>();
            created_rooms.add(FindChatroom(resultSet.getLong(4)));
            use_chatroom.add(FindChatroom(resultSet.getLong(5)));
            User user = new User(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), created_rooms, use_chatroom);
            System.out.println(user);
            users.add(user);
        }
        connection.close();
        return users;
    }

    private User FindUser(Long id) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SelectUser);
        statement.setLong(1, id);
        ResultSet res = statement.executeQuery();
        if(!res.next()) return null;

        return new User(res.getLong(1), res.getString(2), res.getString(3));
    }
    
    private Chatroom FindChatroom(Long id) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SelectChat);
        statement.setLong(1, id);
        ResultSet res = statement.executeQuery();
        if(!res.next()) return null;
        Long chat_id = res.getLong(1);
        String name = res.getString(2);
        String password = res.getString(3);
        connection.close();
        return new Chatroom(chat_id, name, password);
    }
}


class NotSavedSubEntityException extends RuntimeException {
    public NotSavedSubEntityException(String messege) {
        super(messege);
    }
}