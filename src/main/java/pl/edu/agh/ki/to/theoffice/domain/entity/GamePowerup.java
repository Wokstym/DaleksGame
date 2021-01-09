package pl.edu.agh.ki.to.theoffice.domain.entity;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum GamePowerup {
    TELEPORT(0),
    BOMB(0),
    REVERSE_TIME(0);

    private final int defaultCount;

    public static Map<GamePowerup, Integer> toMapWithDefaultValues() {
        return Stream.of(values())
                .collect(Collectors.toMap(Function.identity(), p -> p.defaultCount));
    }

}
