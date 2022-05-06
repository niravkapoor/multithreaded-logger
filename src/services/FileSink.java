package src.services;

import src.common.LogLevel;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FileSink extends Sink{
    private final Lock lock;

    public FileSink() {
        this.lock = new ReentrantLock();
    }

    public void logMessage(String message, LogLevel logLevel) {
        this.lock.lock();
        try {
            System.out.println("logMessage of FileSink " + message);
            FileWriter myWriter = new FileWriter("filename.txt", true);
            myWriter.write(message +"\n");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occured");
            e.printStackTrace();
        } finally {
            this.lock.unlock();
        }
    }
}