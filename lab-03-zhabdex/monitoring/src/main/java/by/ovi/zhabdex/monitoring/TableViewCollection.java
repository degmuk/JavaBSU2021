package by.ovi.zhabdex.monitoring;

import by.degmuk.collection_api.FinalProcessedCollection;
import by.derovi.service_monitoring.visualizer.Table;
import by.zhabdex.common.Service;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TableViewCollection
        implements FinalProcessedCollection<Service, Table> {
    private final String tableName;
    private final List<ColumnProvider> columns;
    private Table table;

    public TableViewCollection(String tableName, List<ColumnProvider> columns) {
        this.tableName = tableName;
        this.columns = columns;
    }

    @Override
    public void renew(Collection<? extends Service> elements) {
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

    static class ColumnProvider {
        final private String title;
        final private Function<Service, String> fieldExtractor;

        private ColumnProvider(String title,
                               Function<Service, String> fieldExtractor) {
            this.title = title;
            this.fieldExtractor = fieldExtractor;
        }

        public static <T> ColumnProvider of(String title,
                                            Function<Service, T> fieldExtractor) {
            return new ColumnProvider(title,
                    x -> fieldExtractor.apply(x).toString());
        }

        public String getTitle() {
            return title;
        }

        public String extractField(Service service) {
            return fieldExtractor.apply(service);
        }
    }
}
