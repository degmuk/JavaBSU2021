package by.ovi.zhabdex.monitoring_lib;

import org.reflections.ReflectionUtils;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ClassMonitoringScanner implements MonitoringScanner {
    @Override
    public Collection<Monitoring> scan(Reflections reflection) {
        var monitors =
                reflection.getSubTypesOf(Monitoring.class);
        var activeMonitorAnnotated =
                reflection.getTypesAnnotatedWith(ActiveMonitoring.class);
        List<Monitoring> result = new ArrayList<>();
        for (var i : monitors) {
            if (!activeMonitorAnnotated.contains(i)) {
                continue;
            }
            try {
                result.add(i.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException ignored) {
            }
        }
        return result;
    }
}
