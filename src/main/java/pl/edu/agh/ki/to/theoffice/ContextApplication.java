package pl.edu.agh.ki.to.theoffice;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.edu.agh.ki.to.theoffice.application.JavaFXApplication;

@SpringBootApplication
public class ContextApplication {

    public static void main(String[] args) {
        javafx.application.Application.launch(JavaFXApplication.class, args);
    }

}
