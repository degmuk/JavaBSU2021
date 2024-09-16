package by.ovi.zhabdex.monitoring_lib;

import by.degmuk.collection_api.FinalProcessedCollection;
import by.derovi.service_monitoring.visualizer.Table;
import by.zhabdex.common.Service;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ContainerMonitoringScanner implements MonitoringScanner {
    @Override
    public Collection<Monitoring> scan(Reflections reflection) {
        var monitoringContainers =
                reflection.getTypesAnnotatedWith(MonitoringContainer.class);
        List<Monitoring> result = new ArrayList<>();
        for (var i : monitoringContainers) {
            try {
                var container = i.getDeclaredConstructor().newInstance();
                var methods = reflection.getMethodsAnnotatedWith(
                        ActiveMonitoring.class);
                for (var method : methods) {
                    if (method.getReturnType().equals(Monitoring.class)) {
                        method.setAccessible(true);
                        result.add((Monitoring) method.invoke(container));
                    } else if (method.getReturnType()
                            .equals(FinalProcessedCollection.class)) {
                        method.setAccessible(true);
                        result.add(new Monitoring() {
                            FinalProcessedCollection<Service, Table>
                                    collection =
                                    (FinalProcessedCollection<Service, Table>) method.invoke(
                                            container);

                            @Override
                            public void update(
                                    Collection<? extends Service> services) {
                                collection.renew(services);
                            }

                            @Override
                            public Table getStatistics() {
                                return collection.currentState();
                            }
                        });
                    }
                }
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
