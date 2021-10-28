package by.ovi.zhabdex.monitoring;

import by.degmuk.collection_api.FinalProcessedCollection;
import by.derovi.service_monitoring.visualizer.Table;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TableViewCollection<T>
        implements FinalProcessedCollection<T, Table> {
    private final String tableName;
    private final List<ColumnProvider<T>> columns;
    private Table table;

    public TableViewCollection(String tableName,
                               List<ColumnProvider<T>> columns) {
        this.tableName = tableName;
        this.columns = columns;
    }

    @Override
    public void renew(Collection<? extends T> elements) {
        table = new Table(tableName);
        table.addRow(columns.stream().map(ColumnProvider::getTitle)
                .collect(Collectors.toList()));
        for (var i : elements) {
            table.addRow(columns.stream().map(x -> x.extractField(i))
                    .collect(Collectors.toList()));
        }
    }

    @Override
    public Table currentState() {
        return table;
    }

    public static class ColumnProvider<T> {
        final private String title;
        final private Function<T, String> fieldExtractor;

        private ColumnProvider(String title,
                               Function<T, String> fieldExtractor) {
            this.title = title;
            this.fieldExtractor = fieldExtractor;
        }

        public static <T, K> ColumnProvider<T> of(
                String title, Function<T, K> fieldExtractor) {
            return new ColumnProvider<T>(
                    title, x -> fieldExtractor.apply(x).toString());
        }

        public String getTitle() {
            return title;
        }

        public String extractField(T service) {
            return fieldExtractor.apply(service);
        }
    }
}
