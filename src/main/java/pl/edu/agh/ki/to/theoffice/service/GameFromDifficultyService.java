package pl.edu.agh.ki.to.theoffice.service;

import org.springframework.stereotype.Service;
import pl.edu.agh.ki.to.theoffice.domain.game.Game;
import pl.edu.agh.ki.to.theoffice.domain.game.GameDifficulty;

import java.lang.reflect.InaccessibleObjectException;

@Service
public class GameFromDifficultyService {

    public Game fromDifficultyType(GameDifficulty difficulty) {
        throw new InaccessibleObjectException("Feature not implemented yet. Use GameFactory.fromProperties() instead");
    }
}
