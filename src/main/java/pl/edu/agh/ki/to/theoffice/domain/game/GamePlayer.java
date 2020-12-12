package pl.edu.agh.ki.to.theoffice.domain.game;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter(AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GamePlayer {

    static GamePlayer fromProperties(GameProperties.GamePlayerProperties properties) {
        final GamePlayer gamePlayer = new GamePlayer();

        gamePlayer.powerups = FXCollections.observableHashMap();
        gamePlayer.powerups.putAll(properties.getPowerups());

        gamePlayer.lives = new SimpleIntegerProperty(properties.getLives());

        return gamePlayer;
    }

    private ObservableMap<GamePowerup, Integer> powerups;
    private IntegerProperty lives;

    public void addPowerup(GamePowerup powerup) {
        this.powerups.compute(powerup, (k, v) -> v += 1);
    }

    public void removePowerup(GamePowerup powerup) {
        this.powerups.compute(powerup, (k, v) -> v -= 1);
    }

    public void addLife() {
        this.lives.add(1);
    }

    public void removeLife() {
        this.lives.subtract(1);
    }

}
