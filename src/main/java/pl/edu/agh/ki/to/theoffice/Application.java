package pl.edu.agh.ki.to.theoffice;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.edu.agh.ki.to.theoffice.application.JavaFXApplication;

import static javafx.application.Application.launch;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        launch(JavaFXApplication.class, args);
    }

}
