package pl.edu.agh.ki.to.theoffice.domain.game;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.edu.agh.ki.to.theoffice.domain.entity.GamePowerup;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;
import pl.edu.agh.ki.to.theoffice.domain.map.move.MapMoveStrategy;
import pl.edu.agh.ki.to.theoffice.domain.map.move.MapMoveStrategyFactory;

import java.util.Map;

import static pl.edu.agh.ki.to.theoffice.domain.map.move.MapMoveStrategy.Type;

@Getter
@Builder
public class GameProperties {

    @Builder.Default
    private GameMapProperties mapProperties = new GameMapProperties(20, 20, Type.BOUNDED);

    @Builder.Default
    private MapMoveStrategy mapMoveStrategy = MapMoveStrategyFactory.fromProperties(new GameMapProperties(20, 20, Type.BOUNDED));

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

    public static class GamePropertiesBuilder {

        private GameMapProperties mapProperties = new GameMapProperties(20, 20, Type.BOUNDED);
        private MapMoveStrategy mapMoveStrategy = MapMoveStrategyFactory.fromProperties(new GameMapProperties(20, 20, Type.BOUNDED));

        public GamePropertiesBuilder mapProperties(GameMapProperties mapProperties) {
            this.mapProperties = mapProperties;
            this.mapMoveStrategy = MapMoveStrategyFactory.fromProperties(mapProperties);
            return this;
        }
    }

    @Getter
    @Builder
    public static class GamePlayerProperties {

        @Builder.Default
        private Map<GamePowerup, Integer> powerups = GamePowerup.toMapWithDefaultValues();

        @Builder.Default
        private int lives = 1;

        private Location initLocation;

        public void addPowerup(GamePowerup powerup, int amount){
            this.powerups.put(powerup, amount);
        }

        public static class GamePlayerPropertiesBuilder {

            private Map<GamePowerup, Integer> powerups = GamePowerup.toMapWithDefaultValues();

            //fixme builder ignores powerup addition
            public GamePlayerPropertiesBuilder powerup(GamePowerup powerup, int amount) {
                this.powerups.put(powerup, amount);
                return this;
            }

        }

    }

}
