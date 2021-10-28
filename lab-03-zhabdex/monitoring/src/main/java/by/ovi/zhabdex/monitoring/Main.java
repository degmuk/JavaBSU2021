package by.ovi.zhabdex.monitoring;

import by.ovi.zhabdex.monitoring_lib.ClassMonitoringScanner;
import by.ovi.zhabdex.monitoring_lib.ContainerMonitoringScanner;
import by.ovi.zhabdex.monitoring_lib.MonitoringApplication;

public class Main {

    public static void main(String[] args) {
        MonitoringApplication
                .builder()
                .packageName("by.ovi.zhabdex.monitoring")
                .serviceURL("http://zhabdex.ovi.by/status")
                .addScanner(new ClassMonitoringScanner())
                .addScanner(new ContainerMonitoringScanner())
                .build()
                .start();
    }
}
