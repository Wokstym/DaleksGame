package pl.edu.agh.ki.to.theoffice.domain.game.properties;

import lombok.Builder;
import lombok.Getter;
import pl.edu.agh.ki.to.theoffice.domain.entity.GamePowerup;

import java.util.Map;

@Getter
@Builder
public class GameProperties {

    private final GamePlayerProperties playerProperties;
    private final int enemies;

    @Getter
    @Builder
    public static class GamePlayerProperties {

        private final Map<GamePowerup, Integer> powerups;
        private final int lives;

        @SuppressWarnings({"FieldMayBeFinal", "unused" })
        public static class GamePlayerPropertiesBuilder {

            private Map<GamePowerup, Integer> powerups = GamePowerup.toMapWithDefaultValues();

            public GamePlayerPropertiesBuilder powerup(GamePowerup powerup, int amount) {
                this.powerups.put(powerup, amount);
                return this;
            }
        }
    }
}
