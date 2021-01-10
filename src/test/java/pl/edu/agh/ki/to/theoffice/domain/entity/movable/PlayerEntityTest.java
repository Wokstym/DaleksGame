package pl.edu.agh.ki.to.theoffice.domain.entity.movable;

import org.junit.jupiter.api.Test;
import pl.edu.agh.ki.to.theoffice.domain.entity.GamePowerup;
import pl.edu.agh.ki.to.theoffice.domain.game.properties.GameProperties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class PlayerEntityTest {

    @Test
    void testFromProperties() {
        // given
        GameProperties gameProperties = mock(GameProperties.class);
        given(gameProperties.getLives()).willReturn(2);

        // when
        PlayerEntity playerEntity = PlayerEntity.fromProperties(gameProperties);

        // then
        assertEquals(gameProperties.getLives(), playerEntity.getLives().get());
        assertEquals(GamePowerup.toMapWithDefaultValues(), playerEntity.getPowerups());
    }

    @Test
    void testAddPowerup() {
        // given
        GameProperties gameProperties = mock(GameProperties.class);
        given(gameProperties.getLives()).willReturn(2);
        PlayerEntity playerEntity = PlayerEntity.fromProperties(gameProperties);

        // when
        playerEntity.addPowerup(GamePowerup.BOMB);

        // then
        assertTrue(playerEntity.getPowerups().containsKey(GamePowerup.BOMB));
        assertEquals(1, playerEntity.getPowerups().get(GamePowerup.BOMB).intValue());
    }

    @Test
    void testRemovePowerup() {
        // given
        GameProperties gameProperties = mock(GameProperties.class);
        given(gameProperties.getLives()).willReturn(2);
        PlayerEntity playerEntity = PlayerEntity.fromProperties(gameProperties);
        playerEntity.addPowerup(GamePowerup.BOMB);

        // when
        playerEntity.removePowerup(GamePowerup.BOMB);

        // then
        assertTrue(playerEntity.getPowerups().containsKey(GamePowerup.BOMB));
        assertEquals(0, playerEntity.getPowerups().get(GamePowerup.BOMB).intValue());
    }

    @Test
    void testCantUsePowerup() {
        // given
        GameProperties gameProperties = mock(GameProperties.class);
        given(gameProperties.getLives()).willReturn(2);
        PlayerEntity playerEntity = PlayerEntity.fromProperties(gameProperties);

        // when then
        assertFalse(playerEntity.canUsePowerup(GamePowerup.BOMB));
        assertFalse(playerEntity.canUsePowerup(GamePowerup.TELEPORT));
    }

    @Test
    void testCanUsePowerup() {
        // given
        GameProperties gameProperties = mock(GameProperties.class);
        given(gameProperties.getLives()).willReturn(2);
        PlayerEntity playerEntity = PlayerEntity.fromProperties(gameProperties);
        playerEntity.addPowerup(GamePowerup.BOMB);

        // when then
        assertTrue(playerEntity.canUsePowerup(GamePowerup.BOMB));
    }

    @Test
    void testAddLife() {
        // given
        GameProperties gameProperties = mock(GameProperties.class);
        given(gameProperties.getLives()).willReturn(2);
        PlayerEntity playerEntity = PlayerEntity.fromProperties(gameProperties);

        // when
        playerEntity.addLife();

        // then
        assertEquals(gameProperties.getLives() + 1, playerEntity.getLives().get());
    }

    @Test
    void testRemoveLife() {
        // given
        GameProperties gameProperties = mock(GameProperties.class);
        given(gameProperties.getLives()).willReturn(2);
        PlayerEntity playerEntity = PlayerEntity.fromProperties(gameProperties);

        // when
        playerEntity.removeLife();

        // then
        assertEquals(gameProperties.getLives() - 1, playerEntity.getLives().get());
    }
}