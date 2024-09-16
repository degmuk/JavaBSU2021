package by.degmuk.collection_api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ReversedCollection<T> implements ProcessedCollection<T,T>{
    Collection<? extends T> data = null;

    @Override
    public void renew(Collection<? extends T> elements) {
        var copy = new ArrayList<>(elements);
        Collections.reverse(copy);
        data = copy;
    }

    @Override
    public Collection<? extends T> currentState() {
        return data;
    }
}
