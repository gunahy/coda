package main.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Kobzar on 16.06.2017.
 */
public class Settings {
    private static String login;
    private static String password;

    public Settings() throws IOException{

        Properties props = new Properties();
        props.load(new FileInputStream(new File("./src/resources/config/settings.conf")));
        login = props.getProperty("LOGIN");
        password = props.getProperty("PASSWORD");
    }

    public static String getLogin() {
        return login;
    }

    public static String getPassword() {
        return password;
    }
}
