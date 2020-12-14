package pl.edu.agh.ki.to.theoffice.domain.entity;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum GamePowerup {

    TELEPORT,
    BOMB;

    public static Map<GamePowerup, Integer> toMap() {
        return Stream.of(values())
                .collect(Collectors.toMap(p -> p, p -> 0));
    }

}
