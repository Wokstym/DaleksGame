package pl.edu.agh.ki.to.theoffice.common.observer;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Change<V> {
    
    public static<V> Change<V> fromState(V stateAfter) {
        return fromState(null, stateAfter);
    }
    
    public static<V> Change<V> fromState(V stateBefore, V stateAfter) {
        return new Change<>(stateBefore, stateAfter);
    }

    private final V stateBefore;
    private final V stateAfter;

}
