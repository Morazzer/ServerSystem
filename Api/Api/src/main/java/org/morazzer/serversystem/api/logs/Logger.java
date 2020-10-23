package org.morazzer.serversystem.api.logs;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.simple.SimpleLogger;
import org.apache.logging.log4j.util.PropertiesUtil;

import java.util.Properties;

/**
 * @author Morazzer
 * @since Date 07.10.2020 22:37:41
 */
public class Logger {

    private static org.apache.logging.log4j.Logger logger = new SimpleLogger("Api", Level.ALL, true, false, true, true, "yyyy-MM-dd HH:mm:ss.SSSZ", null, new PropertiesUtil(new Properties()), System.out);


    public static void info(String m) {
        logger.info(m);
    }

    public static void error(String m) {
        logger.error(m);
    }

    public static void debug(String m) {
        logger.debug(m);
    }

    public static void warn(String m) {
        logger.warn(m);
    }

    public static void trace(String m) {
        logger.trace(m);
    }

    public static org.apache.logging.log4j.Logger getLogger() {
        return logger;
    }
}
