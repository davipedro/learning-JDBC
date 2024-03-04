package br.com.alura.bytebank.data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactoryDB {
    private static final String USER = "postgres";
    private static final String PASSWORD = System.getenv("DB_PASSWORD");
    private static final String URL_CONNECTION = "jdbc:postgresql://localhost/byte_bank";

    public static Connection getConnection(){
        try{
            return createDataSource().getConnection();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private static HikariDataSource createDataSource(){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL_CONNECTION);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.setMaximumPoolSize(10);

        return new HikariDataSource(config);
    }
}
