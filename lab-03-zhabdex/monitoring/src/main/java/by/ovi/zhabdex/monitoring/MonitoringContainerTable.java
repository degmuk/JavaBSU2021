package by.ovi.zhabdex.monitoring;

import by.degmuk.collection_api.*;
import by.derovi.service_monitoring.visualizer.Table;
import by.ovi.zhabdex.monitoring_lib.ActiveMonitoring;
import by.ovi.zhabdex.monitoring_lib.MonitoringContainer;
import by.zhabdex.common.Service;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

@MonitoringContainer
public class MonitoringContainerTable {
    @ActiveMonitoring
    static FinalProcessedCollection<Service, Table> top2Nodes() {
        return new SortedCollection<>(Service::getNodesCount).compose(
                new LimitedCollection<>(2)).compose(
                new TableViewCollection<>("top nodes",
                        List.of(TableViewCollection.ColumnProvider.of("Name",
                                        Service::getName),
                                TableViewCollection.ColumnProvider.of(
                                        "Available nodes",
                                        Service::getNodesCount))));
    }

    @ActiveMonitoring
    static FinalProcessedCollection<Service, Table> summaryPing() {
        return new GroupingCollection<>(Service::getDataCenter).compose(
                        new MappedCollection<>(
                                entry -> new AbstractMap.SimpleEntry<>(entry.getKey(),
                                        entry.getValue().stream().mapToLong(
                                                Service::getRequestsPerSecond).sum())))
                .compose(new TableViewCollection<>("Summary ping",
                        List.of(TableViewCollection.ColumnProvider.of("Name",
                                        Map.Entry::getKey),
                                TableViewCollection.ColumnProvider.of(
                                        "Available nodes",
                                        Map.Entry::getValue))));
    }
}
