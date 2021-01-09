package pl.edu.agh.ki.to.theoffice.domain.game;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
@RequiredArgsConstructor
public class GameManager {

    private final GameFactory gameFactory;

    private Game game;

    @NotNull
    public Game getCurrentGame() {
        if (game == null) {
            throw new IllegalStateException("Game has not been started yet");
        }

        return game;
    }

    public Game createNewGame(GameDifficulty gameDifficulty) {
        this.game = this.gameFactory.createNewGame(gameDifficulty);
        return this.game;
    }

    public Game nextLevel() {
        this.game = this.gameFactory.createNextLevel(this.game);
        return this.game;
    }


}
