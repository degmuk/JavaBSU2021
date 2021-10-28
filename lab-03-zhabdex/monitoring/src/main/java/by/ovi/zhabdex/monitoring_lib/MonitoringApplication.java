package by.ovi.zhabdex.monitoring_lib;

import by.derovi.service_monitoring.visualizer.Table;
import by.derovi.service_monitoring.visualizer.TerminalRenderer;
import by.zhabdex.common.Service;
import by.zhabdex.common.Tools;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ConfigurationBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MonitoringApplication {
    String packageName;
    String serviceUrl;
    List<MonitoringScanner> monitoringScannerList = new ArrayList<>();
    List<Monitoring> monitorings = new ArrayList<>();
    TerminalRenderer renderer;

    private MonitoringApplication() {
    }

    public static MonitoringApplication.Builder builder() {
        var monitoringApplication = new MonitoringApplication();
        return monitoringApplication.new Builder();
    }

    public void start() {
        for (var i : monitoringScannerList) {
            Reflections reflection = new Reflections(
                    new ConfigurationBuilder().addScanners(
                                    new MethodAnnotationsScanner(),
                                    new TypeAnnotationsScanner())
                            .forPackages(packageName));
            monitorings.addAll(i.scan(reflection));
        }
        try {
            renderer = TerminalRenderer.init(monitorings.size());
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            var state = getServicesState();
            var tables = new ArrayList<Table>();
            for (var i : monitorings) {
                i.update(state);
                tables.add(i.getStatistics());
            }
            try {
                renderer.render(tables);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Service> getServicesState() {
        try {
            return Tools.JSON.readValue(makeRequest(), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            return List.of();
        }
    }

    private String makeRequest() {
        try {
            var url = new URL(serviceUrl);
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

    public class Builder {
        private Builder() {
        }

        public MonitoringApplication.Builder packageName(String packageName) {
            MonitoringApplication.this.packageName = packageName;
            return this;
        }

        public MonitoringApplication.Builder serviceURL(String serviceUrl) {
            MonitoringApplication.this.serviceUrl = serviceUrl;
            return this;
        }

        public MonitoringApplication.Builder addScanner(
                MonitoringScanner scanner) {
            MonitoringApplication.this.monitoringScannerList.add(scanner);
            return this;
        }

        public MonitoringApplication build() {
            return MonitoringApplication.this;
        }
    }
}
