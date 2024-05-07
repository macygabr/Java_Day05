package edu.school21.chat.app;

import edu.school21.chat.models.*;
import edu.school21.chat.repositories.*;

import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Optional;


public class Program {
    public static void main ( String[] args ) {
        try {
            DataSource dataSource= new DataSource();
            MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(dataSource.getDataSource());
            Optional<Message> messageOptional = messagesRepository.findById(8L);
               if (messageOptional.isPresent()) {
                Message message = messageOptional.get();
                message.SetText("Bye");
                message.SetDateTime(null);
                messagesRepository.update(message);
              }
            System.out.println(messageOptional.get().toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}