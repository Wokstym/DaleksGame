package pl.edu.agh.ki.to.theoffice.domain.game.properties;

import lombok.Data;
import pl.edu.agh.ki.to.theoffice.domain.game.GameDifficulty;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
public class GamePropertiesConfiguration {

    private Map<GameDifficulty, GameProperties> configurationsByDifficulty;

    @NotNull
    public GameProperties getConfiguration(GameDifficulty difficulty) {
        return configurationsByDifficulty.get(difficulty);
    }


}
