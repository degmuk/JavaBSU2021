package by.degmuk.collection_api;

import java.util.Collection;
import java.util.Optional;
import java.util.function.BinaryOperator;

public class ReducedCollection<T>
        implements FinalProcessedCollection<T, Optional<T>> {
    Optional<T> data = Optional.empty();
    BinaryOperator<T> reducer;

    public ReducedCollection(BinaryOperator<T> reducer) {
        this.reducer = reducer;
    }

    @Override
    public void renew(Collection<? extends T> elements) {
        data = elements.stream().map(el -> (T) el).reduce(reducer);
    }

    @Override
    public Optional<T> currentState() {
        return data;
    }
}
