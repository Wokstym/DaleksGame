package pl.edu.agh.ki.to.theoffice.domain.game;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import static pl.edu.agh.ki.to.theoffice.domain.map.move.MapMoveStrategy.Type;

@Getter
@Builder
public class GameProperties {

    @Builder.Default
    private GameMapProperties mapProperties = new GameMapProperties(20, 20, Type.BOUNDED);

    @Builder.Default
    private GamePlayerProperties playerProperties = GamePlayerProperties.builder().build();

    private int enemies;

    @Getter
    @RequiredArgsConstructor
    public static class GameMapProperties {

        private final int width;
        private final int height;
        private final Type mapMoveStrategy;

    }

    @Getter
    @Builder
    public static class GamePlayerProperties {

        @Builder.Default
        private Map<GamePowerup, Integer> powerups = GamePowerup.toMap();

        @Builder.Default
        private int lives = 1;

        public static class GamePlayerPropertiesBuilder {

            private Map<GamePowerup, Integer> powerups = GamePowerup.toMap();

            public GamePlayerPropertiesBuilder powerup(GamePowerup powerup, int amount) {
                this.powerups.put(powerup, amount);
                return this;
            }

        }

    }

}
