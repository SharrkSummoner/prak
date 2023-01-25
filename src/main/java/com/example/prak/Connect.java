package com.example.prak;

import java.sql.*;
import java.util.Map;


public class Connect {
        public static Connection dbConnect;
        public static Statement statement;
        private  final String nameDB = "book";
        public Connection getDbConnection() throws ClassNotFoundException, SQLException{
            Map<String, String> env = System.getenv();
            String host = env.getOrDefault("DB_HOST", "localhost");;
            String port = env.getOrDefault("DB_PORT", "3306");
            String database = env.getOrDefault("DB_NAME", nameDB);
            String user = env.getOrDefault("DB_USER", "root");
            String password = env.getOrDefault("DB_PASS", "root");

            String connectionUrl = String.format(
                    "jdbc:mysql://%s:%s/%s?serverTimezone=UTC",
                    host,
                    port,
                    database
            );

            if (dbConnect == null) {
                dbConnect = DriverManager.getConnection(
                        connectionUrl,
                        user,
                        password
                );
                System.out.println("Connected to database");
            }

            statement = dbConnect.createStatement();

            return dbConnect;
        }

        public ResultSet getUser(String login, String password){
            ResultSet resSet = null;

            String select = "SELECT * FROM " + nameDB + ".clients WHERE login = ? and password = ?";

            try {
                PreparedStatement prSt = getDbConnection().prepareStatement(select);
                prSt.setString(1, login);
                prSt.setString(2, password);
                resSet = prSt.executeQuery();

            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            return resSet;
        }
    }


