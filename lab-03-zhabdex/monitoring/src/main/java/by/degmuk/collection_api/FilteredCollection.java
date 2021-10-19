package by.degmuk.collection_api;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FilteredCollection<T> implements ProcessedCollection<T, T> {
    final Predicate<T> pred;
    private Collection<? extends T> data = null;

    public FilteredCollection(Predicate<T> pred) {
        this.pred = pred;
    }

    @Override
    public void renew(Collection<? extends T> elements) {
        data = elements.stream().filter(pred).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends T> currentState() {
        return data;
    }
}
