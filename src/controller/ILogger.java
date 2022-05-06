package src.controller;

import src.common.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ILogger {
    void log(LoggerArg arg);

    List<CompletableFuture<String>> getListOfActiveTasks();
}