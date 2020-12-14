package pl.edu.agh.ki.to.theoffice.domain.entity;

import pl.edu.agh.ki.to.theoffice.domain.map.EntityType;

public interface Entity {

    EntityType getType();
    boolean isMovable();
    int getMapPriority();
}
