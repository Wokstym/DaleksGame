package pl.edu.agh.ki.to.theoffice.domain.game.properties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.agh.ki.to.theoffice.domain.entity.GamePowerup;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameProperties {

    private Map<GamePowerup, Integer> powerups;
    private int enemies;
    private int lives;

    public static class GamePropertiesBuilder {

        private Map<GamePowerup, Integer> powerups = GamePowerup.toMapWithDefaultValues();

        public GamePropertiesBuilder powerup(GamePowerup powerup, int amount) {
            this.powerups.put(powerup, amount);
            return this;
        }

    }

}
