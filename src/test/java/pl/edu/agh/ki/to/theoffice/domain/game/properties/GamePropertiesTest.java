package pl.edu.agh.ki.to.theoffice.domain.game.properties;

import org.junit.jupiter.api.Test;
import pl.edu.agh.ki.to.theoffice.domain.entity.GamePowerup;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GamePropertiesTest {

    @Test
    public void testGamePlayerProperties(){
        // given

        // when
        GameProperties.GamePlayerProperties gamePlayerProperties = GameProperties.GamePlayerProperties.builder().build();

        // then
        assertEquals(0, gamePlayerProperties.getLives());
        assertEquals(GamePowerup.toMapWithDefaultValues(), gamePlayerProperties.getPowerups());
    }
}