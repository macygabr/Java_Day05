package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

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
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SelectMessage);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()) return null; 

            User user = FindUser(resultSet.getLong(2));
            Chatroom room = FindChatroom(resultSet.getLong(3));
            optionalMessage = Optional.of(new Message(resultSet.getLong(1), user, room, resultSet.getString(4) , LocalDateTime.now()));
            
        } catch (SQLException e) {
            e.getMessage();
        }
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

    private User FindUser(Long id) throws SQLException {
        String uQuery = "SELECT * FROM Users WHERE login = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(uQuery);
        statement.setLong(1, id);
        ResultSet res = statement.executeQuery();
        if(!res.next()) return null;

        return new User(res.getLong(1), res.getString(2), res.getString(3));
    }
    
    private Chatroom FindChatroom(Long id) throws SQLException {
        String uQuery = "SELECT * FROM Chatroom WHERE id = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(uQuery);
        statement.setLong(1, id);
        ResultSet res = statement.executeQuery();
        if(!res.next()) return null;

        return new Chatroom(res.getLong(1), res.getString(2), res.getString(3));
    }
}

class NotSavedSubEntityException extends RuntimeException {
    public NotSavedSubEntityException(String messege){
        super(messege);
    }
}