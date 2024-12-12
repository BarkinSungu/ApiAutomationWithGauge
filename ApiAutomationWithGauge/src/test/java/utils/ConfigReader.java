package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    static {
        try (FileInputStream fileInputStream = new FileInputStream("src/test/resources/configs/application.properties")) {
            properties = new Properties();
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not read the application.properties file");
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}