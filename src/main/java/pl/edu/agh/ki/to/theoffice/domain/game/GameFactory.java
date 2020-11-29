package pl.edu.agh.ki.to.theoffice.domain.game;

import java.lang.reflect.InaccessibleObjectException;

public class GameFactory {

    public static Game fromDifficultyType(GameDifficulty difficulty) {
        throw new InaccessibleObjectException("Feature not implemented yet. Use GameFactory.fromProperties() instead");
    }

    public static Game fromProperties(GameProperties properties) {
        return Game.fromProperties(properties);
    }

}
