package br.com.alura.bytebank.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    private static final String USER = "postgres";
    private static final String PASSWORD = "D02dps$";
    private static final String URL_CONNECTION = "jdbc:postgresql://localhost/byte_bank";

    private static Connection connection;

    private ConnectionDB() {
    }

    public static Connection getConnection(){
        if (connection == null){
            try{
                connection = DriverManager.getConnection(URL_CONNECTION, USER, PASSWORD);
            }
            catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
        return connection;
    }

    public static void closeConnection(){
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
    }
}
