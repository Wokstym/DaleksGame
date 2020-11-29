package pl.edu.agh.ki.to.theoffice.domain.game;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

// todo: add support for different game difficulties

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum GameDifficulty {

    EASY,
    NORMAL,
    HARD;

}
