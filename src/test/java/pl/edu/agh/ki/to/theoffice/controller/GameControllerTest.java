package pl.edu.agh.ki.to.theoffice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class GameControllerTest {

    @Autowired
    private GameController gameController;

    @Test
    public void test1() {
        assertEquals(4, 2 + 2);
    }

}
