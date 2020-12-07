package pl.edu.agh.ki.to.theoffice.domain.map;

import com.sun.javafx.collections.ObservableMapWrapper;
import javafx.collections.MapChangeListener;
import org.springframework.lang.Nullable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ObservableLinkedMultiValueMap<K, V> extends ObservableMapWrapper<K, List<V>> {

    private final LinkedMultiValueMap<K, V> backingMap;

    public ObservableLinkedMultiValueMap(LinkedMultiValueMap<K, V> backingMap) {
        super(backingMap);
        this.backingMap = backingMap;
    }

    public void add(K key, @Nullable V value) {
        backingMap.add(key, value);
        this.callObservers(new SimpleChange(key, null, Collections.singletonList(value), true, false));
    }

    public void addAll(MultiValueMap<K, V> values) {
        backingMap.addAll(values);

        for (Entry<K, List<V>> entry : values.entrySet()) {
            this.callObservers(new SimpleChange(entry.getKey(), null, entry.getValue(), true, false));
        }
    }

    public Map<K, V> toSingleValueMap() {
        return backingMap.toSingleValueMap();
    }

    private class SimpleChange extends MapChangeListener.Change<K, List<V>> {
        private final K key;
        private final List<V> old;
        private final List<V> added;
        private final boolean wasAdded;
        private final boolean wasRemoved;

        public SimpleChange(K key, List<V> old, List<V> added, boolean wasAdded, boolean wasRemoved) {
            super(ObservableLinkedMultiValueMap.this);

            assert wasAdded || wasRemoved;

            this.key = key;
            this.old = old;
            this.added = added;
            this.wasAdded = wasAdded;
            this.wasRemoved = wasRemoved;
        }

        public boolean wasAdded() {
            return this.wasAdded;
        }

        public boolean wasRemoved() {
            return this.wasRemoved;
        }

        public K getKey() {
            return this.key;
        }

        public List<V> getValueAdded() {
            return this.added;
        }

        public List<V> getValueRemoved() {
            return this.old;
        }

        public String toString() {
            StringBuilder result = new StringBuilder();
            if (this.wasAdded) {
                if (this.wasRemoved) {
                    result.append(this.old).append(" replaced by ").append(this.added);
                } else {
                    result.append(this.added).append(" added");
                }
            } else {
                result.append(this.old).append(" removed");
            }

            result.append(" at key ").append(this.key);
            return result.toString();
        }
    }
}
