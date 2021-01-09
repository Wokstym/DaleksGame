package pl.edu.agh.ki.to.theoffice.domain.entity.movable;

import org.junit.jupiter.api.Test;
import pl.edu.agh.ki.to.theoffice.domain.entity.GamePowerup;
import pl.edu.agh.ki.to.theoffice.domain.game.properties.GamePropertiesConfiguration;

import static org.junit.jupiter.api.Assertions.*;

class PlayerEntityTest {

//    @Test
//    void testFromProperties() {
//        // given
//        GamePropertiesConfiguration.PlayerProperties gamePlayerProperties = GamePropertiesConfiguration.PlayerProperties.builder().build();
//
//        // when
//        PlayerEntity playerEntity = PlayerEntity.fromProperties(gamePlayerProperties);
//
//        // then
//        assertEquals(gamePlayerProperties.getLives(), playerEntity.getLives().get());
//        assertEquals(gamePlayerProperties.getPowerups(), playerEntity.getPowerups());
//    }
//
//    @Test
//    void testAddPowerup() {
//        // given
//        GamePropertiesConfiguration.PlayerProperties gamePlayerProperties = GamePropertiesConfiguration.PlayerProperties.builder().build();
//        PlayerEntity playerEntity = PlayerEntity.fromProperties(gamePlayerProperties);
//
//        // when
//        playerEntity.addPowerup(GamePowerup.BOMB);
//
//        // then
//        assertTrue(playerEntity.getPowerups().containsKey(GamePowerup.BOMB));
//        assertEquals(1, playerEntity.getPowerups().get(GamePowerup.BOMB).intValue());
//    }
//
//    @Test
//    void testRemovePowerup() {
//        // given
//        GamePropertiesConfiguration.PlayerProperties gamePlayerProperties = GamePropertiesConfiguration.PlayerProperties.builder().build();
//        PlayerEntity playerEntity = PlayerEntity.fromProperties(gamePlayerProperties);
//        playerEntity.addPowerup(GamePowerup.BOMB);
//
//        // when
//        playerEntity.removePowerup(GamePowerup.BOMB);
//
//        // then
//        assertTrue(playerEntity.getPowerups().containsKey(GamePowerup.BOMB));
//        assertEquals(0, playerEntity.getPowerups().get(GamePowerup.BOMB).intValue());
//    }
//
//    @Test
//    void testCantUsePowerup(){
//        // given
//        GamePropertiesConfiguration.PlayerProperties gamePlayerProperties = GamePropertiesConfiguration.PlayerProperties.builder().build();
//        PlayerEntity playerEntity = PlayerEntity.fromProperties(gamePlayerProperties);
//
//        // when then
//        assertFalse(playerEntity.canUsePowerup(GamePowerup.BOMB));
//        assertFalse(playerEntity.canUsePowerup(GamePowerup.TELEPORT));
//    }
//
//    @Test
//    void testCanUsePowerup(){
//        // given
//        GamePropertiesConfiguration.PlayerProperties gamePlayerProperties = GamePropertiesConfiguration.PlayerProperties.builder().build();
//        PlayerEntity playerEntity = PlayerEntity.fromProperties(gamePlayerProperties);
//        playerEntity.addPowerup(GamePowerup.BOMB);
//
//        // when then
//        assertTrue(playerEntity.canUsePowerup(GamePowerup.BOMB));
//    }
//
//    @Test
//    void testAddLife() {
//        // given
//        GamePropertiesConfiguration.PlayerProperties gamePlayerProperties = GamePropertiesConfiguration.PlayerProperties.builder().build();
//        PlayerEntity playerEntity = PlayerEntity.fromProperties(gamePlayerProperties);
//
//        // when
//        playerEntity.addLife();
//
//        // then
//        assertEquals(1, playerEntity.getLives().get());
//    }
//
//    @Test
//    void testRemoveLife() {
//        // given
//        GamePropertiesConfiguration.PlayerProperties gamePlayerProperties = GamePropertiesConfiguration.PlayerProperties.builder().build();
//        PlayerEntity playerEntity = PlayerEntity.fromProperties(gamePlayerProperties);
//        playerEntity.addLife();
//
//        // when
//        playerEntity.removeLife();
//
//        // then
//        assertEquals(0, playerEntity.getLives().get());
//    }
}