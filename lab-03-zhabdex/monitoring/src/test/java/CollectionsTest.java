import by.degmuk.collection_api.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CollectionsTest {
    @Test
    void FilteredCollectionTest() {
        var v = List.of(1, 2, 3, 4, 8, 9, 11);
        var filter = new FilteredCollection<Integer>(x -> x % 2 == 0);
        filter.renew(v);
        assertEquals(filter.currentState(), v.stream().filter(x -> x % 2 == 0)
                .collect(Collectors.toList()));
    }

    @Test
    void LimitedCollectionTest() {
        var v = List.of(1, 2, 3, 4, 8, 9, 11);
        var limit = new LimitedCollection<Integer>(3);
        limit.renew(v);
        assertEquals(limit.currentState(),
                v.stream().limit(3).collect(Collectors.toList()));
    }

    @Test
    void MappedCollectionTest() {
        var v = List.of(1, 2, 3, 4, 8, 9, 11);
        var map = new MappedCollection<Integer, Double>(Math::sqrt);
        map.renew(v);
        assertEquals(map.currentState(),
                v.stream().map(Math::sqrt).collect(Collectors.toList()));
    }

    @Test
    void ReducedCollectionTest() {
        var v = List.of(1, 2, 3, 4, 8, 9, 11);
        var reducer = new ReducedCollection<Integer>(Integer::sum);
        reducer.renew(v);
        assertEquals(reducer.currentState(), v.stream().reduce(Integer::sum));
    }

    @Test
    void ReversedCollectionTest() {
        var v = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 8, 9, 11));
        var reverser = new ReversedCollection<Integer>();
        reverser.renew(v);
        Collections.reverse(v);
        assertEquals(reverser.currentState(), v);
    }

    @Test
    void SortedCollectionTest() {
        var v = new ArrayList<>(Arrays.asList(9, 2, 3, 1, 8, 4, 11));
        var sorter = new SortedCollection<>(Integer::compare);
        sorter.renew(v);
        Collections.sort(v);
        assertEquals(sorter.currentState(), v);
    }
}
