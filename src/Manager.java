package src;

import src.services.*;
import src.common.*;
import src.controller.*;
import java.util.concurrent.ExecutionException;

public class Manager {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Sink logSink = new FileSink();
        LogController controller = new LogController(2, logSink);
        
        LoggerArg arg = new LoggerArg("message 1", LogLevel.Error);
        controller.log(arg);

        arg = new LoggerArg("message 2", LogLevel.Warning);
        controller.log(arg);

        arg = new LoggerArg("message 3", LogLevel.Fatal);
        controller.log(arg);
        
        arg = new LoggerArg("message 4", LogLevel.Fatal);
        controller.log(arg);

        arg = new LoggerArg("message 5", LogLevel.Fatal);
        controller.log(arg);
        
        arg = new LoggerArg("message 6", LogLevel.Fatal);
        controller.log(arg);
        
        arg = new LoggerArg("message 7", LogLevel.Fatal);
        controller.log(arg);

        arg = new LoggerArg("message 8", LogLevel.Fatal);
        controller.log(arg);

        controller.flush();
    }
}