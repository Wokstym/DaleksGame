package pl.edu.agh.ki.to.theoffice.domain.entity;

public interface Entity {

    EntityType getType();

    boolean isMovable();

    default int getMapPriority() {
        return getType().getMapPriority();
    }
}
