package fordsoft.tech.mydesk;


import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MydeskApplication {

    public static void main(String[] args) {
        Application.launch(FxApplication.class, args);
    }

}
