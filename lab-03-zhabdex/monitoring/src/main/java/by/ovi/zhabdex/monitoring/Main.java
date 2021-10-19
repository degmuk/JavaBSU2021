package by.ovi.zhabdex.monitoring;

import by.degmuk.collection_api.SortedCollection;
import by.derovi.service_monitoring.visualizer.TerminalRenderer;
import by.zhabdex.common.Service;
import by.zhabdex.common.Tools;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        var renderer = TerminalRenderer.init(1);
        while (true) {
            var collection = new SortedCollection<>(
                    Service::getRequestsPerSecond).compose(
                    new TableViewCollection("Test",
                            List.of(TableViewCollection.ColumnProvider.of(
                                            "Name", Service::getName),
                                    TableViewCollection.ColumnProvider.of(
                                            "Data center",
                                            Service::getDataCenter),
                                    TableViewCollection.ColumnProvider.of(
                                            "Ping", Service::getAveragePing),
                                    TableViewCollection.ColumnProvider.of(
                                            "Available nodes",
                                            Service::getNodesCount),
                                    TableViewCollection.ColumnProvider.of(
                                            "Requests/sec",
                                            Service::getRequestsPerSecond),
                                    TableViewCollection.ColumnProvider.of(
                                            "Started time",
                                            Service::getStartedTime),
                                    TableViewCollection.ColumnProvider.of(
                                            "Current time",
                                            Service::getCurrentTime))));
            collection.renew(getServicesState());
            renderer.render(List.of(collection.currentState()));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static List<Service> getServicesState() {
        try {
            return Tools.JSON.readValue(makeRequest(), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            return List.of();
        }
    }

    private static String makeRequest() {
        try {
            var url = new URL("http://zhabdex.ovi.by/status");
            URLConnection urlConnection = url.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder response = new StringBuilder();
            while (in.ready()) {
                response.append(in.readLine());
            }
            in.close();
            return response.toString();
        } catch (IOException e) {
            return "";
        }
    }

    private static List<String> getServiceCharacteristics(Service service) {
        return List.of(service.getName(), service.getDataCenter(),
                Long.toString(service.getAveragePing()),
                Long.toString(service.getNodesCount()),
                Long.toString(service.getRequestsPerSecond()),
                service.getStartedTime().toString(),
                service.getCurrentTime().toString());
    }
}
