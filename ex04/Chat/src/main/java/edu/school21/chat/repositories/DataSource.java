
package edu.school21.chat.repositories;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;
import java.io.InputStream;
import java.util.Scanner;

public class DataSource {
    String url =  "jdbc:postgresql://localhost:5432/postgres";
    String user = "macygabr";
    String password = "password";
    
    private HikariDataSource dataSource;

    public DataSource() throws SQLException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);
        dataSource = new HikariDataSource(config);

        UpdateData("schema.sql");
        UpdateData("data.sql");
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    private void UpdateData(String file) throws SQLException {
        Connection con = this.getConnection();
        Statement st = con.createStatement();
        InputStream input = DataSource.class.getClassLoader().getResourceAsStream(file);
        Scanner scanner = new Scanner(input).useDelimiter(";");
        while (scanner.hasNext()) st.executeUpdate(scanner.next().trim());
    }
}
