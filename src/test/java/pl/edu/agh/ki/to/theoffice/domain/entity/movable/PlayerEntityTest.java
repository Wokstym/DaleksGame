package pl.edu.agh.ki.to.theoffice.domain.game;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.agh.ki.to.theoffice.domain.entity.GamePowerup;
import pl.edu.agh.ki.to.theoffice.domain.entity.movable.PlayerEntity;
import pl.edu.agh.ki.to.theoffice.domain.game.properties.GameProperties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class PlayerEntityTest {

    @Test
    void testFromProperties() {
        // given
        GameProperties.GamePlayerProperties gamePlayerProperties = GameProperties.GamePlayerProperties.builder().build();

        // when
        PlayerEntity playerEntity = PlayerEntity.fromProperties(gamePlayerProperties);

        // then
        assertEquals(gamePlayerProperties.getLives(), playerEntity.getLives().get());
        assertEquals(gamePlayerProperties.getPowerups(), playerEntity.getPowerups());
    }

    @Test
    void testAddPowerup() {
        // given
        GameProperties.GamePlayerProperties gamePlayerProperties = GameProperties.GamePlayerProperties.builder().build();
        PlayerEntity playerEntity = PlayerEntity.fromProperties(gamePlayerProperties);

        // when
        playerEntity.addPowerup(GamePowerup.BOMB);

        // then
        assertTrue(playerEntity.getPowerups().containsKey(GamePowerup.BOMB));
        assertEquals(1, playerEntity.getPowerups().get(GamePowerup.BOMB).intValue());
    }

    @Test
    void testRemovePowerup() {
        // given
        GameProperties.GamePlayerProperties gamePlayerProperties = GameProperties.GamePlayerProperties.builder().build();
        PlayerEntity playerEntity = PlayerEntity.fromProperties(gamePlayerProperties);
        playerEntity.addPowerup(GamePowerup.BOMB);

        // when
        playerEntity.removePowerup(GamePowerup.BOMB);

        // then
        assertTrue(playerEntity.getPowerups().containsKey(GamePowerup.BOMB));
        assertEquals(0, playerEntity.getPowerups().get(GamePowerup.BOMB).intValue());
    }

    @Test
    void testAddLife() {
        // given
        GameProperties.GamePlayerProperties gamePlayerProperties = GameProperties.GamePlayerProperties.builder().build();
        PlayerEntity playerEntity = PlayerEntity.fromProperties(gamePlayerProperties);

        // when
        playerEntity.addLife();

        // then
        assertEquals(2, playerEntity.getLives().get());
    }

    @Test
    void testRemoveLife() {
        // given
        GameProperties.GamePlayerProperties gamePlayerProperties = GameProperties.GamePlayerProperties.builder().build();
        PlayerEntity playerEntity = PlayerEntity.fromProperties(gamePlayerProperties);

        // when
        playerEntity.removeLife();

        // then
        assertEquals(0, playerEntity.getLives().get());
    }
}