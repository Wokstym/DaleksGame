package pl.edu.agh.ki.to.theoffice.domain.entity;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GamePowerupTest {

    @Test
    void testToMapWithDefaultValues() {
        // given

        // when
        Map<GamePowerup, Integer> powerupIntegerMap = GamePowerup.toMapWithDefaultValues();

        // then
        Arrays.stream(GamePowerup.values()).forEach(gamePowerup -> {
            assertTrue(powerupIntegerMap.containsKey(gamePowerup));
            assertEquals(0, powerupIntegerMap.get(gamePowerup).intValue());
        });
    }
}