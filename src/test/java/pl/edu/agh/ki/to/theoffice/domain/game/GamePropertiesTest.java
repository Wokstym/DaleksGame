package pl.edu.agh.ki.to.theoffice.domain.game;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.agh.ki.to.theoffice.domain.entity.GamePowerup;
import pl.edu.agh.ki.to.theoffice.domain.map.move.MapMoveStrategy;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GamePropertiesTest {

    @Test
    public void testGamePlayerProperties(){
        // given

        // when
        GameProperties.GamePlayerProperties gamePlayerProperties = GameProperties.GamePlayerProperties.builder().build();

        // then
        assertEquals(1, gamePlayerProperties.getLives());
        assertEquals(GamePowerup.toMapWithDefaultValues(), gamePlayerProperties.getPowerups());
    }

    @Test
    public void testGameMapProperties(){
        // given

        // when
        GameProperties.GameMapProperties gameMapProperties = new GameProperties.GameMapProperties(20, 20, MapMoveStrategy.Type.BOUNDED);

        // then
        assertEquals(20, gameMapProperties.getWidth());
        assertEquals(20, gameMapProperties.getHeight());
        assertEquals(MapMoveStrategy.Type.BOUNDED, gameMapProperties.getMapMoveStrategy());
    }
}