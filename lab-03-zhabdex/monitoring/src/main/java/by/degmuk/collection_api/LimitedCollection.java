package by.degmuk.collection_api;

import java.util.Collection;
import java.util.stream.Collectors;

public class LimitedCollection<T> implements ProcessedCollection<T,T> {
    Collection<? extends T> data = null;
    final long maxSize;

    public LimitedCollection(long maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public void renew(Collection<? extends T> elements) {
        data = elements.stream().limit(maxSize).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends T> currentState() {
        return data;
    }
}
