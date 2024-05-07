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
            messagesRepository.findAll(0, 4);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}