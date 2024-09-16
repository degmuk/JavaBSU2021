package by.degmuk.collection_api;

import java.util.Collection;

public interface ProcessedCollection<T, E>
        extends FinalProcessedCollection<T, Collection<? extends E>> {

    default <U> ProcessedCollection<T, U> compose(
            ProcessedCollection<E, U> collection) {
        return new ComposedCollection<T, E, U>(this, collection);
    }

    default <U> FinalProcessedCollection<T, U> compose(
            FinalProcessedCollection<E, U> collection) {
        return new FinalComposedCollection<T, E, U>(this, collection);
    }
}

class ComposedCollection<T, E, U> implements ProcessedCollection<T, U> {
    ProcessedCollection<T, E> coll1;
    ProcessedCollection<E, U> coll2;

    public ComposedCollection(ProcessedCollection<T, E> coll1,
                              ProcessedCollection<E, U> coll2) {
        this.coll1 = coll1;
        this.coll2 = coll2;
    }

    @Override
    public void renew(Collection<? extends T> elements) {
        coll1.renew(elements);
        coll2.renew(coll1.currentState());
    }

    @Override
    public Collection<? extends U> currentState() {
        return coll2.currentState();
    }
}


class FinalComposedCollection<T, E, U>
        implements FinalProcessedCollection<T, U> {
    ProcessedCollection<T, E> coll1;
    FinalProcessedCollection<E, U> coll2;

    public FinalComposedCollection(ProcessedCollection<T, E> coll1,
                                   FinalProcessedCollection<E, U> coll2) {
        this.coll1 = coll1;
        this.coll2 = coll2;
    }

    @Override
    public void renew(Collection<? extends T> elements) {
        coll1.renew(elements);
        coll2.renew(coll1.currentState());
    }

    @Override
    public U currentState() {
        return coll2.currentState();
    }
}
