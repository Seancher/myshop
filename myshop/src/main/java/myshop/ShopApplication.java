package myshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by sergey on 30.11.15.
 */
@SpringBootApplication
public class ShopApplication {
    public static void main(String[] args){
        // Settings parameters for MySQL database
        // java -Dhost=X -Dport=X -Duser=X -Dpassword=X -Ddatabase=X -jar /path/to/Application.jar
        MySqlSettings.host = System.getProperty("host", "localhost");
        MySqlSettings.port = System.getProperty("port", "3306");
        MySqlSettings.database = System.getProperty("database", "myshop");
        MySqlSettings.user = System.getProperty("user", "root");
        MySqlSettings.password = System.getProperty("password", "");

        SpringApplication.run(ShopApplication.class, args);
    }
}
