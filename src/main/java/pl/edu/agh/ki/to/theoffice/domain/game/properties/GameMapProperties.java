package pl.edu.agh.ki.to.theoffice.domain.game.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GameMapProperties {

    private final int width;
    private final int height;
}