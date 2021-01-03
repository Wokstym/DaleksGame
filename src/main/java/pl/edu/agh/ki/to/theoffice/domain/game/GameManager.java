package pl.edu.agh.ki.to.theoffice.domain.game;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.agh.ki.to.theoffice.domain.game.properties.GamePropertiesConfiguration;

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

    public void createNewGame(GameDifficulty gameDifficulty) {
        this.game = this.gameRepository.fromDifficulty(gameDifficulty);
    }

}
