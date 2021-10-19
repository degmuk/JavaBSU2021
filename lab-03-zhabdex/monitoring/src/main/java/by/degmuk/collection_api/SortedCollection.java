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

    public SortedCollection(Function<T, ? extends Number> keyExtractor) {
        this.comparator = Comparator.comparingLong(
                v -> keyExtractor.apply(v).longValue());
    }

    public SortedCollection(Function<T, ? extends Number> keyExtractor,
                            boolean isReversed) {
        if (isReversed) {
            this.comparator = Comparator.comparingLong(
                    v -> -keyExtractor.apply(v).longValue());
        } else {
            this.comparator = Comparator.comparingLong(
                    v -> keyExtractor.apply(v).longValue());
        }
    }

    @Override
    public void renew(Collection<? extends T> elements) {
        data =
                elements.stream().sorted(comparator).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends T> currentState() {
        return data;
    }
}
