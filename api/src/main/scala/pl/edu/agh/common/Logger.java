package pl.edu.agh.common;

// TODO implement proper logger
public class Logger {

    private static final int WARN = 0;
    private static final int INFO = 1;
    private static final int DEBUG = 2;
    private static final int LEVEL = WARN;

    public void info(String log) {
        log(INFO, log);
    }

    private void log(int level, String log) {
        if (LEVEL <= level) {
            System.out.println(log);
        }
    }

}
