package pl.edu.agh.ki.to.theoffice.domain.map.move;

import java.lang.reflect.InaccessibleObjectException;

import static pl.edu.agh.ki.to.theoffice.domain.game.GameProperties.GameMapProperties;

public class MapMoveStrategyFactory {

    public static MapMoveStrategy fromProperties(GameMapProperties mapProperties) {
        return switch (mapProperties.getMapMoveStrategy()) {
            case BOUNDED -> new BoundedMapMoveStrategy(mapProperties.getWidth(), mapProperties.getHeight());
            // todo: implement wrapped move strategy
            case WRAPPED -> throw new InaccessibleObjectException("Feature not implemented yet. Use Type.BOUNDED instead");
        };
    }

}
