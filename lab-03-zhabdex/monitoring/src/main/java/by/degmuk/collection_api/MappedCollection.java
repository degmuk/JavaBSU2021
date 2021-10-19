package by.degmuk.collection_api;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MappedCollection<T, E> implements ProcessedCollection<T, E> {
    final Function<T, E> mapper;
    private Collection<? extends E> data = null;

    public MappedCollection(Function<T, E> mapper) {
        this.mapper = mapper;
    }

    @Override
    public void renew(Collection<? extends T> elements) {
        data = elements.stream().map(mapper).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends E> currentState() {
        return data;
    }
}
