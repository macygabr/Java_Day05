package edu.school21.chat.app;

import edu.school21.chat.models.*;
import edu.school21.chat.repositories.*;

import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;


public class Program {
    public static void main ( String[] args ) {
        try {
            DataSource dataSource= new DataSource();

            Update("schema.sql", dataSource);
            Update("data.sql", dataSource);

            User creator = new User(7L, "user", "user", new ArrayList(), new ArrayList());
            User author = creator;
            Chatroom room = new Chatroom(8L, author, creator, new ArrayList());
            Message message = new Message(9l, author, room, "Hello!", LocalDateTime.now());
            MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(dataSource.getDataSource());
            messagesRepository.save(message);
            System.out.println(message.GetId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private static void Update(String file, DataSource dataSource) throws SQLException {
        Connection con = dataSource.getConnection();
        Statement st = con.createStatement();
        InputStream input = Program.class.getClassLoader().getResourceAsStream(file);
        Scanner scanner = new Scanner(input).useDelimiter(";");
        while (scanner.hasNext()) {
            st.executeUpdate(scanner.next().trim());
        }
    }

}