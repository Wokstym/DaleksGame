package pl.edu.agh.ki.to.theoffice;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.edu.agh.ki.to.theoffice.application.JavaFXApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        javafx.application.Application.launch(JavaFXApplication.class, args);
    }

}
