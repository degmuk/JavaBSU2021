package by.degmuk.collection_api;

import java.util.*;
import java.util.function.Function;

public class GroupingCollection<T, E> implements
        ProcessedCollection<T, Map.Entry<? extends E, ? extends List<?
                extends T>>> {

    final Function<? super T, ? extends E> classifier;
    Map<E, List<T>> data = null;

    public GroupingCollection(Function<? super T, ? extends E> classifier) {
        this.classifier = classifier;
    }

    @Override
    public void renew(Collection<? extends T> elements) {
        data = new HashMap<>();
        for (var i : elements) {
            var key = classifier.apply(i);
            if (!data.containsKey(key)) {
                data.put(key, new ArrayList<T>());
            }
            data.get(key).add(i);
        }
    }

    @Override
    public Collection<? extends Map.Entry<? extends E, ? extends List<?
            extends T>>> currentState() {
        return data.entrySet();
    }
}
