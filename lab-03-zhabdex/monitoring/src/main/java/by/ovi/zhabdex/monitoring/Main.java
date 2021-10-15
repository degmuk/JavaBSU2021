package by.ovi.zhabdex.monitoring;

import by.derovi.service_monitoring.visualizer.Table;
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
            var services = getServicesState();
            var table = new Table("Zhabdex services").addRow("Name", "Data Center",
                    "Ping", "Available nodes", "Requests/sec", "Started time",
                    "Current time");
            for (var i : services) {
                table.addRow(getServiceCharacteristics(i));
            }
            renderer.render(List.of(table));
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
