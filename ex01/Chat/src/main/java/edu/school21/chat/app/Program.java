package edu.school21.chat.app;
import edu.school21.chat.models.*;
import edu.school21.chat.repositories.*;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;


public class Program {
    public static void main ( String[] args ) {        
        try {
            DataSource dataSource= new DataSource();
            MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(dataSource.getDataSource());
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter a message ID\n -> ");
            Optional<Message> byId = messagesRepository.findById(scanner.nextLong());
            System.out.println(byId.get().toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}