package src.services;

import src.common.*;
import java.util.concurrent.CompletableFuture;

public class Process {
    private Sink fileSink;
    private String processId;

    public Process(Sink fileSink, String processId) {
        this.fileSink = fileSink;
        this.processId = processId;
    }

    public String GetProcessId() {
        return this.processId;
    }

    public CompletableFuture<String> start(String message, LogLevel logLevel) {
        System.out.println("start method for " + " " + this.processId);
        // s
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                fileSink.logMessage(message, logLevel);
                System.out.println("start method end " + message + " " + this.processId);
            } catch(Exception e) {
                throw e;
            }
            return this.processId;
        });
        return future;
    }
}
