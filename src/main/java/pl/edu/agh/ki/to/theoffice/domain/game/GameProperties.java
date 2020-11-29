package pl.edu.agh.ki.to.theoffice.domain.game;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static pl.edu.agh.ki.to.theoffice.domain.map.move.MapMoveStrategy.Type;

@Getter
@Builder
public class GameProperties {

    @Builder.Default
    private GameMapProperties mapProperties = new GameMapProperties(20, 20, Type.BOUNDED);
    private int enemies;

    @Getter
    @RequiredArgsConstructor
    public static class GameMapProperties {

        private final int width;
        private final int height;
        private final Type mapMoveStrategy;

    }

}
