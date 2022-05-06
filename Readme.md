## Logger: Low level system design

- A logger implementation designed for multithreaded environments, dealing with concurrency and locking mechanisms. 


## System Design
- `Sink.java` is a base class which can be inherited by any kind of logging service (File, Memory, Database)
- `FileSink.java` log the information in the specific file.
- `LogController.java`:
    Responsible for calling the sink service.
    Maintains the queue of log requests that cannot be served currently.
    As one log request get's completes, request is been fetched from queue.
- `Process.java`, starts executing the log request on a separate thread     using completable future.
- `Manager.java` is the driver class.


## Concepts

- `Completable Future`: are used to perform asynchronous computations, specifically ending processes without blocking the main thread.
    `supplyAsync` - It will execute the steps in a separate thread.


## How to run:
1. It will compile the java code:  javac src/Manager.java
2. Run using: java src/Manager
