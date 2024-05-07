package edu.school21.chat.repositories;
import edu.school21.chat.models.Message;

import java.sql.SQLException;
import java.util.Optional;

import java.util.List;
import edu.school21.chat.models.User;

public interface MessagesRepository {
    Optional<Message> findById (Long id) throws SQLException;
    void save(Message message) throws Exception;
    void update(Message message)throws Exception;
    List<User> findAll(int page, int size) throws Exception;
}   