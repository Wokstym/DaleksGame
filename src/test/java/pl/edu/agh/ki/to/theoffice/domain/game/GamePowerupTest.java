package pl.edu.agh.ki.to.theoffice.domain.game;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.agh.ki.to.theoffice.domain.entity.GamePowerup;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GamePowerupTest {

    @Test
    void testToMap() {
        // given

        // when
        Map <GamePowerup, Integer> powerupIntegerMap = GamePowerup.toMap();

        // then
        Arrays.stream(GamePowerup.values()).forEach(gamePowerup -> {
            assertTrue(powerupIntegerMap.containsKey(gamePowerup));
            assertEquals(0, powerupIntegerMap.get(gamePowerup).intValue());
        });
    }
}