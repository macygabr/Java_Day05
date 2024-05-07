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
    private final String selectQuery = "SELECT * FROM Message WHERE id = ?";

    public MessagesRepositoryJdbcImpl ( DataSource dataSource ) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById ( Long id ) throws SQLException {
        Optional<Message> optionalMessage = Optional.empty();
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(selectQuery);
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        if(!resultSet.next()) return null; 

        User user = FindUser(resultSet.getString(2));
        Chatroom room = FindChatroom(resultSet.getLong(3));
        optionalMessage = Optional.of(new Message(resultSet.getLong(1), user, room, resultSet.getString(4)));
            
        return optionalMessage;
    }

    private User FindUser(String name) throws SQLException {
        String uQuery = "SELECT * FROM Users WHERE login = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(uQuery);
        statement.setString(1, name);
        ResultSet rs = statement.executeQuery();
        if(!rs.next()) return null;

        return new User(rs.getLong(1), rs.getString(2), rs.getString(3));
    }
    
    private Chatroom FindChatroom(Long id) throws SQLException {
        String uQuery = "SELECT * FROM Chatroom WHERE id = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(uQuery);
        statement.setLong(1, id);
        ResultSet rs = statement.executeQuery();
        if(!rs.next()) return null;

        return new Chatroom(rs.getLong(1), rs.getString(2), rs.getString(3));
    }
}