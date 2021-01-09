package pl.edu.agh.ki.to.theoffice.domain.game;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
@RequiredArgsConstructor
public class GameManager {

    private final GameRepository gameRepository;

    private Game game;

    @NotNull
    public Game getCurrentGame() {
        if(game == null) {
            throw new IllegalStateException("Game has not been started yet");
        }

        return game;
    }

    public Game createNewGame(GameDifficulty gameDifficulty) {
        this.game = this.gameRepository.createNewGame(gameDifficulty);
        return this.game;
    }

    public Game nextLevel() {
        this.game = this.gameRepository.createNextLevel(this.game);
        return this.game;
    }

}
