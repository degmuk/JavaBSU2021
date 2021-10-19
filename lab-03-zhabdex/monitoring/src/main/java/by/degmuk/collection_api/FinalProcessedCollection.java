package by.degmuk.collection_api;

import java.util.Collection;

public interface FinalProcessedCollection<T, E> {
    void renew(Collection<? extends T> elements);

    E currentState();
}
