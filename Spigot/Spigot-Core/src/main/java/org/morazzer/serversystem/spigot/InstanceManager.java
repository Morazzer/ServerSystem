package org.morazzer.serversystem.spigot;

import org.morazzer.serversystem.spigot.impl.api.RankApiImpl;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Morazzer
 * @since Date 17.10.2020 12:29:12
 */
public class InstanceManager {

    private final static ConcurrentHashMap<Class<?>, Object> objects = new ConcurrentHashMap<>();
    public static void setInstance(Object o) {
        objects.put(o.getClass().getInterfaces()[0], o);
    }

    public static Object getInstance(Class<?> interfaceClass) {
        return objects.get(interfaceClass);
    }
}
