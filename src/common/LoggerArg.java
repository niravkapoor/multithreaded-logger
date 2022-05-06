package src.common;

public class LoggerArg {
    public String message;
    public LogLevel logLevel;

    public LoggerArg(String message, LogLevel logLevel) {
        this.message = message;
        this.logLevel = logLevel;
    }
}