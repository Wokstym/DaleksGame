package pl.edu.agh.ki.to.theoffice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.agh.ki.to.theoffice.controller.GameController;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ApplicationIntegrationTest {

    @Autowired
    public GameController gameController;

    @Test
    public void testContextSetup() {
        assertNotNull(gameController);
    }

}
