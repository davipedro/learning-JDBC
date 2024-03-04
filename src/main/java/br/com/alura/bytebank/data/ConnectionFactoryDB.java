package br.com.alura.bytebank.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactoryDB {

    private static final String USER = "postgres";
    private static final String PASSWORD = "D02dps$";
    private static final String URL_CONNECTION = "jdbc:postgresql://localhost/byte_bank";

    private Connection connection;

    public Connection getConnection(){
        try{
            connection = DriverManager.getConnection(URL_CONNECTION, USER, PASSWORD);
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
        return connection;
    }

    public void closeConnection(){
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
    }
}
