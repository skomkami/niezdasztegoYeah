package pl.edu.agh.common;

// TODO implement proper logger
public class AppLogger {

    private static final int WARN = 0;
    private static final int INFO = 1;
    private static final int DEBUG = 2;
    private static final int LEVEL = WARN;

    public static void info(String log) {
        log(INFO, "INFO: " + log);
    }

    public static void warn(String log) {
        log(WARN, "WARN: " + log);
    }

    public static void debug(String log) {
        log(DEBUG, "DEBUG: " + log);
    }

    private static void log(int level, String log) {
        if (LEVEL <= level) {
            System.out.println(log);
        }
    }

}
