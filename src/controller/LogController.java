package src.controller;

import src.common.*;
import src.services.*;
import src.services.Process;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import static java.util.concurrent.CompletableFuture.allOf;

public class LogController implements ILogger {
    private final Lock lock;
    private int bufferSize;
    private Sink sinkService;
    private Set<String> set = new HashSet<String>();
    private Queue<LoggerArg> queue = new LinkedList<LoggerArg>();
    private List<CompletableFuture<String>> tasks = new ArrayList<>();

    public LogController(int bufferSize, Sink sinkService) {
        this.bufferSize = bufferSize;
        this.sinkService = sinkService;
        this.lock = new ReentrantLock();
    }

    public void log(LoggerArg arg) {
        try {
            if (this.isBufferAvailable()) {
                this.logMessage(arg.message, arg.logLevel);
            } else {
                queue.add(arg);
            }
        } catch(Exception ex) {

        }
    }

    public List<CompletableFuture<String>> getListOfActiveTasks() {
        return this.tasks;
    }
    
    private Boolean isBufferAvailable() {
        if (this.set.size() < this.bufferSize) {
            return true;
        }

        return false;
    }

    public void flush() throws InterruptedException, ExecutionException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                if(!this.queue.isEmpty()) {
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch(InterruptedException e) {
            //    throw e;
            }
            return "";
        });
        tasks.add(future);
        allOf(tasks.toArray(CompletableFuture[]::new)).get();
    }

    private void logMessage(String message, LogLevel logLevel) {
        String processId = "" + System.currentTimeMillis();
        Process process = new Process(this.sinkService, processId);
        set.add(processId);
        CompletableFuture<String> future = process.start(message, logLevel);
        tasks.add(future);
        // thenApply will be executed in main thread.
        future.thenApply(data -> {
            System.out.println("future completed for " + data);
            set.remove(data);
            System.out.println("removed for " + data);
            this.pollFromQueue();
            return "";
        });
    }

    private void pollFromQueue() {
        try {
            this.lock.lock();
            while(!this.queue.isEmpty() && this.set.size() < bufferSize) {
                LoggerArg arg = this.queue.poll();
                System.out.println("fetched from queu for " + arg.message);
                this.logMessage(arg.message, arg.logLevel);
            }
        } catch(Exception ex) {

        } finally {
            this.lock.unlock();
        }
    }
}