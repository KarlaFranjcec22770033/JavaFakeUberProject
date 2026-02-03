package database;

import utils.DialogAlert;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public interface MyConnection {

    public static Connection getConnection() {
        try(FileReader fr=new FileReader("src/main/resources/database.properties")){
            Properties prop=new Properties();
            prop.load(fr);

            String url=prop.getProperty("database");
            String user=prop.getProperty("user");
            String pass=prop.getProperty("pass");

            return DriverManager.getConnection(url,user,pass);

        }catch(Exception e){
            DialogAlert.error("error connecting to database");
            return null;
        }
    }
}
