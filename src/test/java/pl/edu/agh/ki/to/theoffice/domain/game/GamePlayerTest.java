package pl.edu.agh.ki.to.theoffice.domain.game;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GamePlayerTest {

    @Test
    void testFromProperties() {
        // given
        GameProperties.GamePlayerProperties gamePlayerProperties = GameProperties.GamePlayerProperties.builder().build();

        // when
        GamePlayer gamePlayer = GamePlayer.fromProperties(gamePlayerProperties);

        // then
        assertEquals(gamePlayerProperties.getLives(), gamePlayer.getLives().get());
        assertEquals(gamePlayerProperties.getPowerups(), gamePlayer.getPowerups());
    }

    @Test
    void testAddPowerup() {
        // given
        GameProperties.GamePlayerProperties gamePlayerProperties = GameProperties.GamePlayerProperties.builder().build();
        GamePlayer gamePlayer = GamePlayer.fromProperties(gamePlayerProperties);

        // when
        gamePlayer.addPowerup(GamePowerup.BOMB);

        // then
        assertTrue(gamePlayer.getPowerups().containsKey(GamePowerup.BOMB));
        assertEquals(1, gamePlayer.getPowerups().get(GamePowerup.BOMB).intValue());
    }

    @Test
    void testRemovePowerup() {
        // given
        GameProperties.GamePlayerProperties gamePlayerProperties = GameProperties.GamePlayerProperties.builder().build();
        GamePlayer gamePlayer = GamePlayer.fromProperties(gamePlayerProperties);
        gamePlayer.addPowerup(GamePowerup.BOMB);

        // when
        gamePlayer.removePowerup(GamePowerup.BOMB);

        // then
        assertTrue(gamePlayer.getPowerups().containsKey(GamePowerup.BOMB));
        assertEquals(0, gamePlayer.getPowerups().get(GamePowerup.BOMB).intValue());
    }

    @Test
    void testAddLife() {
        // given
        GameProperties.GamePlayerProperties gamePlayerProperties = GameProperties.GamePlayerProperties.builder().build();
        GamePlayer gamePlayer = GamePlayer.fromProperties(gamePlayerProperties);

        // when
        gamePlayer.addLife();

        // then
        assertEquals(2, gamePlayer.getLives().get());
    }

    @Test
    void testRemoveLife() {
        // given
        GameProperties.GamePlayerProperties gamePlayerProperties = GameProperties.GamePlayerProperties.builder().build();
        GamePlayer gamePlayer = GamePlayer.fromProperties(gamePlayerProperties);

        // when
        gamePlayer.removeLife();

        // then
        assertEquals(0, gamePlayer.getLives().get());
    }
}