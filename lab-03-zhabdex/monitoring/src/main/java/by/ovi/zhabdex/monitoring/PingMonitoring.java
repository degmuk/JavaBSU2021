package by.ovi.zhabdex.monitoring;

import by.degmuk.collection_api.FinalProcessedCollection;
import by.degmuk.collection_api.SortedCollection;
import by.derovi.service_monitoring.visualizer.Table;
import by.ovi.zhabdex.monitoring_lib.ActiveMonitoring;
import by.ovi.zhabdex.monitoring_lib.Monitoring;
import by.zhabdex.common.Service;

import java.util.Collection;
import java.util.List;


@ActiveMonitoring
public class PingMonitoring implements Monitoring {
    FinalProcessedCollection<Service, Table> collection =
            new SortedCollection<>(Service::getAveragePing).compose(
                    new TableViewCollection("Ping monitoring", List.of(
                            TableViewCollection.ColumnProvider.of("Name", Service::getName),
                            TableViewCollection.ColumnProvider.of("Data center", Service::getDataCenter),
                            TableViewCollection.ColumnProvider.of("Ping", Service::getAveragePing),
                            TableViewCollection.ColumnProvider.of("Requests/sec", Service::getRequestsPerSecond),
                            TableViewCollection.ColumnProvider.of("Started time", Service::getStartedTime),
                            TableViewCollection.ColumnProvider.of("Current time", Service::getCurrentTime)
                    )));

    @Override
    public void update(Collection<? extends Service> services) {
        collection.renew(services);
    }

    @Override
    public Table getStatistics() {
        return collection.currentState();
    }
}
