package org.example;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Set;

public class DataBaseConnection {

    private static final String DB_PROPS ="db.properties" ;
    private static final String DB_URL = "db.url";
    public static final String DB_PASSWORD = "db.password";
    public static final String DB_USERNAME = "db.username";

    private static Properties loadProperties(){
        try(InputStream is =DataBaseConnection.class.getClassLoader().getResourceAsStream(DB_PROPS)){
            Properties dbProperties = new Properties();
            dbProperties.load(is);
            return dbProperties;

        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        DataSource dataSource = initDataSource();
        return dataSource.getConnection();
    }

    public static void closeConnection(Connection conn) throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    private static DataSource initDataSource(){
        Properties properties = loadProperties();
        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        dataSource.setUser(properties.getProperty(DB_USERNAME));
        dataSource.setPassword(properties.getProperty(DB_PASSWORD));
        dataSource.setURL(properties.getProperty(DB_URL));

        return dataSource;
    }

}

