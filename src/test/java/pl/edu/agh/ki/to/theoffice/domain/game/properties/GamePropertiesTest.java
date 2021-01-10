package pl.edu.agh.ki.to.theoffice.domain.game.properties;

import org.junit.jupiter.api.Test;
import pl.edu.agh.ki.to.theoffice.domain.entity.GamePowerup;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GamePropertiesTest {

    @Test
    public void testGameProperties() {
        // given

        // when
        GameProperties gameProperties = GameProperties.builder().build();

        // then
        assertEquals(0, gameProperties.getLives());
        assertEquals(0, gameProperties.getEnemies());
        assertEquals(GamePowerup.toMapWithDefaultValues(), gameProperties.getPowerups());
    }
}