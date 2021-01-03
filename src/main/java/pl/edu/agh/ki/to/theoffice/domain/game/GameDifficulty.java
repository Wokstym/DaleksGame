package pl.edu.agh.ki.to.theoffice.domain.game;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum GameDifficulty {

    EASY("Łatwy"),
    NORMAL("Średni"),
    HARD("Trudny");

    private final String name;


    @Override
    public String toString() {
        return this.name;
    }

}
