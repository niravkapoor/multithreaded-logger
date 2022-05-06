package src.services;

import src.common.LogLevel;
;
public abstract class Sink {
    public abstract void logMessage(String message, LogLevel logLevel);
}
