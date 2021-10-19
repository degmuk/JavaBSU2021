package by.degmuk.collection_api;


import java.util.Collection;

public interface ProcessedCollection<T, E> extends
        FinalProcessedCollection<T, Collection<? extends E>> {}
