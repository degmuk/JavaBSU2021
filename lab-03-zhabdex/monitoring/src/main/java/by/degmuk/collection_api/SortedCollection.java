package by.degmuk.collection_api;

import java.util.Collection;
import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SortedCollection<T> implements ProcessedCollection<T, T> {

    Comparator<T> comparator;
    Collection<? extends T> data;

    public SortedCollection(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public <C extends Comparable<C>> SortedCollection(
            Function<T, C> keyExtractor, boolean isReversed) {
        if (isReversed) {
            this.comparator = Comparator.comparing(keyExtractor);
        } else {
            this.comparator = (a, b) -> -keyExtractor.apply(a)
                    .compareTo(keyExtractor.apply(b));
        }
    }

    public <C extends Comparable<C>> SortedCollection(
            Function<T, C> keyExtractor) {
        this.comparator = Comparator.comparing(keyExtractor);
    }

    @Override
    public void renew(Collection<? extends T> elements) {
        data = elements.stream().sorted(comparator)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends T> currentState() {
        return data;
    }
}
