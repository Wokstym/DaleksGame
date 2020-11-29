package pl.edu.agh.ki.to.theoffice.domain.map.move;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
abstract class BaseGameMapMoveStrategy implements MapMoveStrategy {

    protected final int mapWidth;
    protected final int mapHeight;

}
