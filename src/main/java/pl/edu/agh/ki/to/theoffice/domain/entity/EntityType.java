package pl.edu.agh.ki.to.theoffice.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EntityType {

    PLAYER(1),
    ENEMY(3),
    ENEMY_SCRAP(2),
    DEAD_PLAYER(0),
    BOMB(2),
    TELEPORT(2),
    REVERSE_TIME(2);

    private final int mapPriority;
}
