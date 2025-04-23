package com.raman.oopscoder.posts;

import javax.servlet.*;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadsImpl {

    public static void main(String[] args) throws Exception {
        // 1. thread vs runnable
        System.out.println("Current thread: " + Thread.currentThread().getName());
        // Current thread: main

        MyThread t1 = new MyThread();
        t1.start(); // Starts a new thread
        // MyThread's thread: Thread-0

        Thread t2 = new Thread(new MyRunnable());
        t2.start(); // Starts a new thread
        // MyRunnable's thread: Thread-1

        // 2. synchronized
        SharedResource resource = new SharedResource();
        new Thread(resource::incrementUsingSynchronizedMethod).start();
        new Thread(resource::incrementUsingSynchronizedBlock).start();
        new Thread(resource::incrementUsingSynchronizedLockObject).start();

        // [Synchronized Method] Thread-2: 1
        // [Synchronized Lock Object] Thread-4: 2
        // [Synchronized Method] Thread-2: 3
        // [Synchronized Lock Object] Thread-4: 4
        // [Synchronized Method] Thread-2: 5
        // [Synchronized Lock Object] Thread-4: 6
        // [Synchronized Block] Thread-3: 7
        // [Synchronized Block] Thread-3: 8
        // [Synchronized Block] Thread-3: 9

        // 3. wait, notify, join
        SimpleProducerConsumer simpleProducerConsumer = new SimpleProducerConsumer();

        Thread producer = new Thread(() -> {
            try {
                Thread.sleep(1000); // Simulate delay of 1000 ms
                simpleProducerConsumer.produce(19);
            } catch (InterruptedException e) { /* handle it */ }
        });

        Thread consumer = new Thread(() -> {
            try {
                int received = simpleProducerConsumer.consume();
            } catch (InterruptedException e) { /* handle it */ }
        });

        producer.start(); // Starts Producer thread
        consumer.start(); // Starts Consumer thread

        // Use join() to wait for both threads to finish
        producer.join(); // Main thread waits for producer to finish
        consumer.join(); // Main thread waits for consumer to finish

        System.out.println("Main thread: Producer and Consumer finished.");

        // Consumer: Waiting for data...
        // Producer: Producing data...
        // Producer: Data produced and notified.
        // Consumer: Data received = 19
        // Main thread: Producer and Consumer finished.

        // 4. LockSupport
        Thread worker1 = new Thread(() -> {
            System.out.println("WorkerThread-1: Waiting to be unparked...");
            LockSupport.park();  // thread will block here
            System.out.println("WorkerThread-1: Unparked and resumed.");
        });

        worker1.start();

        try {
            Thread.sleep(1000); /* Simulate delay */
        } catch (InterruptedException e) { /* handle it */ }

        System.out.println("Main thread: Unparking worker thread.");
        LockSupport.unpark(worker1);  // unblock the worker thread

        // WorkerThread-1: Waiting to be unparked...
        // Main thread: Unparking worker thread.
        // WorkerThread-1: Unparked and resumed.

        Thread worker2 = new Thread(() -> {
            System.out.println("WorkerThread-2: Started execution...");
            try {
                Thread.sleep(1000); /* Simulate delay */
            } catch (InterruptedException e) { /* handle it */ }
            System.out.println("WorkerThread-2: Parking worker thread.");
            LockSupport.park(); // will not block because it was already unparked
            System.out.println("WorkerThread-2: Resumes immediately!");
        });

        worker2.start();
        LockSupport.unpark(worker2); // pre-unpark — gives a "permit" before actual parking
        System.out.println("Main thread: Unparked worker thread.");

        // Main thread: Unparked worker thread.
        // WorkerThread-2: Started execution...
        // WorkerThread-2: Parking worker thread.
        // WorkerThread-2: Resumes immediately!

        // 6. volatile
        Worker worker = new Worker();
        Thread t3 = new Thread(worker::run);
        t3.start();

        // let the thread t3 run for 2 seconds
        Thread.sleep(2000);

        System.out.println("Main thread: stopping worker...");
        worker.stopRunning();

        // wait for the worker to finish
        t3.join();
        System.out.println("Main thread: worker has stopped.");

        // Thread-5: started.
        // Main thread: stopping worker...
        // Thread-5: stopped.
        // Main thread: worker has stopped.

        // 7. atomic data types
        AtomicInteger counter = new AtomicInteger(0);
        AtomicBoolean printed = new AtomicBoolean(false);

        Runnable task = () -> {
            for (int i = 0; i < 2; i++) {
                int current = counter.incrementAndGet();
                System.out.println(Thread.currentThread().getName() + ": Count = " + current);

                // Only one thread prints this when count reaches 1
                if (current >= 1 && printed.compareAndSet(false, true))
                    System.out.println(Thread.currentThread().getName() + ": Count reached 1! (printed only once)");

                // Simulate delay
                try { Thread.sleep(100); } catch (InterruptedException e) { /* handle it */ }
            }
        };

        Thread t4 = new Thread(task, "Thread-1");
        Thread t5 = new Thread(task, "Thread-2");

        t4.start(); t5.start();
        t4.join(); t5.join();

        System.out.println("Main thread: Final Count = " + counter.get());

        // Thread-2: Count = 2
        // Thread-1: Count = 1
        // Thread-2: Count reached 1! (printed only once)
        // Thread-1: Count = 3
        // Thread-2: Count = 4
        // Main thread: Final Count = 4

        // 8. ReentrantLock
        SimplePrinterQueue printer = new SimplePrinterQueue();

        Runnable colorTask = () -> printer.printJob("color");
        Runnable bwTask = () -> printer.printJob("bw");

        for (int i = 0; i < 2; i++) {
            new Thread(colorTask, "ColorThread-" + i).start();
            new Thread(bwTask, "BWThread-" + i).start();
        }

        // ColorThread-0: Printing a color job...
        // ColorThread-0: Finished printing.
        // BWThread-0: Printing a bw job...
        // BWThread-1: Could not acquire lock. Skipping bw job.
        // ColorThread-1: Could not acquire lock. Skipping color job.
        // BWThread-0: Finished printing.

        // 9. Semaphore
        /* *** *** *** *** *** test code *** *** *** *** *** */
        DatabaseConnectionPool pool = new DatabaseConnectionPool();

        // Create 5 threads trying to connect
        for (int i = 0; i < 5; i++) {
            new Thread(pool::connect, "DatabaseConnectionPoolThread-" + i).start();
        }

        // DatabaseConnectionPoolThread-0: Connected
        // DatabaseConnectionPoolThread-2: Connected
        // DatabaseConnectionPoolThread-1: Connected
        // DatabaseConnectionPoolThread-2: Disconnected
        // DatabaseConnectionPoolThread-1: Disconnected
        // DatabaseConnectionPoolThread-4: Connected
        // DatabaseConnectionPoolThread-0: Disconnected
        // DatabaseConnectionPoolThread-3: Connected
        // DatabaseConnectionPoolThread-4: Disconnected
        // DatabaseConnectionPoolThread-3: Disconnected

        // 10. Fork/Join Framework
        int[] array = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        try (ForkJoinPool forkJoinPool = ForkJoinPool.commonPool()) {
            SumTask sumTask = new SumTask(array, 0, array.length);

            long result = forkJoinPool.invoke(sumTask);
            System.out.println("Sum of [1..10] = " + result);
        }

        // Sum of [1..10] = 55

        // 11. Executor Framework
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> System.out.println("Running in background"));
        ((ExecutorService) executor).shutdown();    // Hack to shut down the executor

        // Running in background

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<Integer> future = executorService.submit(new MyCallable());
        System.out.println("Task done: " + future.isDone());    // false
        System.out.println("Future result: " + future.get());   // blocks until the task is done, then prints 1
        executorService.shutdown();

        // Task done: false
        // Future result: 1

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(() -> System.out.println("Delayed task"), 1, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(() -> System.out.println("Fixed-Rate scheduled task"), 1, 1, TimeUnit.SECONDS);
        scheduler.scheduleWithFixedDelay(() -> System.out.println("Fixed-Delay scheduled task"), 1, 2, TimeUnit.SECONDS);

        Thread.sleep(3000); // Main thread sleeps for 3 seconds, so that scheduler can run a few iterations

        scheduler.shutdown();
        boolean terminated = scheduler.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("Scheduler terminated: " + terminated);

        // Delayed task
        // Fixed-Rate scheduled task
        // Fixed-Delay scheduled task
        // Fixed-Rate scheduled task
        // Fixed-Rate scheduled task
        // Fixed-Delay scheduled task
        // Scheduler terminated: true

        // Custom ThreadPoolExecutor
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(
                2,  // Core threads
                3, // Max threads
                60, // Keep-alive
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(5),  // Bounded queue
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()   // Fallback
        );

        // Submit tasks
        for (int i = 0; i < 10; i++) {
            tpe.execute(() -> {
                System.out.printf("Task running in %s [Active threads: %d, Queue size: %d, Completed tasks: %d]%n",
                        Thread.currentThread().getName(), tpe.getActiveCount(), tpe.getQueue().size(), tpe.getCompletedTaskCount());
            });
        }

        tpe.shutdown(); // Graceful shutdown

        // Task running in pool-4-thread-1 [Active threads: 2, Queue size: 0, Completed tasks: 0]
        // Task running in pool-4-thread-2 [Active threads: 2, Queue size: 5, Completed tasks: 0]
        // Task running in main [Active threads: 3, Queue size: 5, Completed tasks: 0]
        // Task running in pool-4-thread-3 [Active threads: 3, Queue size: 5, Completed tasks: 0]
        // Task running in pool-4-thread-3 [Active threads: 3, Queue size: 3, Completed tasks: 3]
        // Task running in pool-4-thread-3 [Active threads: 3, Queue size: 2, Completed tasks: 4]
        // Task running in pool-4-thread-1 [Active threads: 3, Queue size: 4, Completed tasks: 1]
        // Task running in pool-4-thread-2 [Active threads: 3, Queue size: 3, Completed tasks: 2]
        // Task running in pool-4-thread-3 [Active threads: 3, Queue size: 1, Completed tasks: 5]
        // Task running in pool-4-thread-1 [Active threads: 3, Queue size: 0, Completed tasks: 6]

        // 12. CompletableFuture
        // run async task (no return value)
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            System.out.println("Running in background");
        }); // Running in background

        // supply async task (returns value)
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            return "Hello World";
        });

        // blocking get
        String result = future2.get();

        // non-blocking callback
        future2.thenAccept(result1 -> System.out.println("Result: " + result1));    // Result: Hello World

        // thenApply() - transform result
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> "Hello")
                .thenApply(s -> s + " World")
                .thenApply(String::toUpperCase);

        future3.thenAccept(System.out::println);    // HELLO WORLD

        // thenCompose() - chain dependent futures
        CompletableFuture<String> getUser = CompletableFuture.supplyAsync(() -> "user123");
        CompletableFuture<String> getOrder = getUser.thenCompose(user ->
                CompletableFuture.supplyAsync(() -> "Order for " + user)
        );

        getOrder.thenAccept(System.out::println);   // Order for user123

        // thenCombine() - merge two futures
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> "World");

        hello.thenCombine(world, (h, w) -> h + " " + w)
                .thenAccept(System.out::println); // "Hello World"

        // allOf() - wait for all futures
        CompletableFuture<Void> all = CompletableFuture.allOf(
                CompletableFuture.supplyAsync(() -> "Task1"),
                CompletableFuture.supplyAsync(() -> "Task2")
        );

        all.thenRun(() -> System.out.println("All tasks completed"));   // All tasks completed

        // exceptionally() - fallback Value
        CompletableFuture.supplyAsync(() -> {
                    if (Math.random() > 0.5) throw new RuntimeException("Error!");
                    return "Success";
                })
                .exceptionally(ex -> "Fallback: " + ex.getMessage())
                .thenAccept(System.out::println);   // Fallback: java.lang.RuntimeException: Error!

        // handle() - success/failure in one method
        CompletableFuture.supplyAsync(() -> "Process data")
                .handle((result2, ex) -> {
                    if (ex != null) return "Error occurred";
                    return result2.toUpperCase();
                });

        // 13. ThreadLocal variables
        // In any service or DAO class, you can access the transaction ID without passing it as a parameter.
        String txnId = RequestContext.getTransactionId();
        System.out.println("Processing order for Transaction ID: " + txnId);

        // 14. Count Down Latch
        /* *** *** *** *** *** test code *** *** *** *** *** */
        CountDownLatch latch = new CountDownLatch(3);   // Initialize latch with count=3 (for 3 services)

        // Start service initialization threads
        new Thread(new Service("Auth", 1000, latch)).start();
        new Thread(new Service("Cache", 1500, latch)).start();
        new Thread(new Service("Database", 2000, latch)).start();

        // Main thread waits for all services
        System.out.println("Waiting for services to initialize...");
        latch.await();

        // Proceed when count reaches 0
        System.out.println("All services are ready! Starting application...");

        // Waiting for services to initialize...
        // Auth service initialized
        // Cache service initialized
        // Database service initialized
        // All services are ready! Starting application...

        // 15. ConcurrentHashMap
        ConcurrentHashMap<String, Integer> counter1 = new ConcurrentHashMap<>();

        Runnable task1 = () -> {
            for (int i = 0; i < 1000; i++) {
                counter1.merge("count", 1, (oldVal, newVal) -> oldVal + newVal);
            }
        };

        Thread t6 = new Thread(task1);
        Thread t7 = new Thread(task1);

        t6.start(); t7.start();
        t6.join(); t7.join();

        System.out.println("Thread-safe total count: " + counter1.get("count"));

        // Thread-safe total count: 2000

        // 16. Virtual Threads
        // Create virtual thread (Option 1: Thread.startVirtualThread)
        Thread vThread = Thread.startVirtualThread(() -> {
            System.out.println(Thread.currentThread() + ": Hello from virtual thread!");
        });

        // Wait for completion
        try { vThread.join(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        // VirtualThread[#58]/runnable@ForkJoinPool-1-worker-1: Hello from virtual thread!

        // Create virtual thread (Option 2: Builder pattern)
        Thread.ofVirtual()
                .name("my-virtual-thread")
                .start(() -> {
                    System.out.println(Thread.currentThread() + ": Virtual thread with custom name");
                });

        // VirtualThread[#60,my-virtual-thread]/runnable@ForkJoinPool-1-worker-2: Virtual thread with custom name

        // Create virtual threads with ExecutorService
        try (ExecutorService executor2 = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 5; i++) {
                int taskId = i;
                executor2.submit(() -> {
                    System.out.println(Thread.currentThread() + ": Executor task " + taskId + " running");
                    Thread.sleep(500);  // sleep is non-blocking in virtual threads
                    return null;
                });
            }
        } // Auto-close waits for all tasks

        // VirtualThread[#63]/runnable@ForkJoinPool-1-worker-2: Executor task 0 running
        // VirtualThread[#67]/runnable@ForkJoinPool-1-worker-5: Executor task 4 running
        // VirtualThread[#65]/runnable@ForkJoinPool-1-worker-3: Executor task 2 running
        // VirtualThread[#64]/runnable@ForkJoinPool-1-worker-1: Executor task 1 running
        // VirtualThread[#66]/runnable@ForkJoinPool-1-worker-4: Executor task 3 running

        // A Million Threads (Impossible with Platform Threads)
        for (int i = 0; i < 1_000_000; i++) {
            int taskId = i;
            Thread.startVirtualThread(() -> {
                System.out.println(Thread.currentThread() + ": Task " + taskId + " running");
                try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            });
        }

        // VirtualThread[#1000079]/runnable@ForkJoinPool-1-worker-4: Task 999997 running
        // VirtualThread[#1000080]/runnable@ForkJoinPool-1-worker-4: Task 999998 running
        // VirtualThread[#1000081]/runnable@ForkJoinPool-1-worker-4: Task 999999 running
        // VirtualThread[#997930]/runnable@ForkJoinPool-1-worker-8: Task 997848 running
        // VirtualThread[#997919]/runnable@ForkJoinPool-1-worker-1: Task 997837 running
        // VirtualThread[#1000002]/runnable@ForkJoinPool-1-worker-7: Task 999920 running

    }

    static class MyThread extends Thread {
        public void run() {
            System.out.println("MyThread's thread: " + Thread.currentThread().getName());
        }
    }

    static class MyRunnable implements Runnable {
        public void run() {
            System.out.println("MyRunnable's thread: " + Thread.currentThread().getName());
        }
    }

    static class SharedResource {
        private final Object lock = new Object();
        private int count = 1;

        public synchronized void incrementUsingSynchronizedMethod() {
            for (int i = 1; i <= 3; i++) {
                System.out.println("[Synchronized Method] " + Thread.currentThread().getName() + ": " + count++);
            }
        }

        public void incrementUsingSynchronizedBlock() {
            synchronized (this) {
                for (int i = 1; i <= 3; i++) {
                    System.out.println("[Synchronized Block] " + Thread.currentThread().getName() + ": " + count++);
                }
            }
        }

        public void incrementUsingSynchronizedLockObject() {
            synchronized (lock) {
                for (int i = 1; i <= 3; i++) {
                    System.out.println("[Synchronized Lock Object] " + Thread.currentThread().getName() + ": " + count++);
                }
            }
        }
    }

    static class SimpleProducerConsumer {
        private int data;
        private boolean ready = false;

        public synchronized void produce(int value) {
            System.out.println("Producer: Producing data = " + value);
            data = value;
            ready = true;
            notify(); // Notify the waiting consumer
            System.out.println("Producer: Data produced and notified.");
        }

        public synchronized int consume() throws InterruptedException {
            while (!ready) {
                System.out.println("Consumer: Waiting for data...");
                wait(); // Wait until data is produced
            }
            System.out.println("Consumer: Data received = " + data);
            return data;
        }
    }

    static class Worker {
        private volatile boolean isRunning = true;  // visible across threads

        public void run() {
            System.out.println(Thread.currentThread().getName() + ": started.");
            while (isRunning) { /* simulate some work */ }
            // stops when isRunning=false (reads from main memory)
            System.out.println(Thread.currentThread().getName() + ": stopped.");
        }

        public void stopRunning() {
            isRunning = false;
        }
    }

    static class SimpleAtomicExample {
        static AtomicInteger counter = new AtomicInteger(0);
        static AtomicBoolean printed = new AtomicBoolean(false);

        public static void main(String[] args) throws InterruptedException {
            Runnable task = () -> {
                for (int i = 0; i < 3; i++) {
                    int current = counter.incrementAndGet();
                    System.out.println(Thread.currentThread().getName() + ": Count = " + current);

                    // Only one thread prints this when count reaches 5
                    if (current >= 3 && printed.compareAndSet(false, true))
                        System.out.println(Thread.currentThread().getName() + ": Count reached 3! (printed only once)");

                    // Simulate delay
                    try { Thread.sleep(100); } catch (InterruptedException e) { /* handle it */ }
                }
            };

            Thread t1 = new Thread(task, "Thread-1");
            Thread t2 = new Thread(task, "Thread-2");

            t1.start();
            t2.start();

            t1.join();
            t2.join();

            System.out.println("Final count: " + counter.get());
        }
    }

    static class SimplePrinterQueue {

        private final ReentrantLock lock = new ReentrantLock(true); // fair lock
        private final Condition colorQueue = lock.newCondition();
        private final Condition bwQueue = lock.newCondition();
        private boolean printerBusy = false;

        public void printJob(String jobType) {
            boolean acquired = false;
            try {
                // Try to acquire lock with timeout
                acquired = lock.tryLock(1, TimeUnit.SECONDS);
                if (!acquired) {
                    System.out.println(Thread.currentThread().getName() + ": Could not acquire lock. Skipping " + jobType + " job.");
                    return;
                }

                Condition currentCondition = jobType.equals("color") ? colorQueue : bwQueue;

                // Wait if printer is busy
                while (printerBusy) {
                    System.out.println(Thread.currentThread().getName() + ": Waiting in " + jobType + " queue.");
                    currentCondition.await(); // wait until printer is busy
                }

                // Proceed to print
                printerBusy = true;
                System.out.println(Thread.currentThread().getName() + ": Printing a " + jobType + " job...");
                Thread.sleep(500); // simulate print time

                printerBusy = false;
                System.out.println(Thread.currentThread().getName() + ": Finished printing.");

                // Notify all waiting threads
                colorQueue.signal();
                bwQueue.signal();

            } catch (InterruptedException e) {
                // handle it
            } finally {
                if (acquired) {
                    lock.unlock();
                }
            }
        }
    }

    static class SumTask extends RecursiveTask<Long> {
        private static final int THRESHOLD = 3;
        private final int[] arr;
        private final int start, end;

        public SumTask(int[] arr, int start, int end) {
            this.arr = arr;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            if (end - start <= THRESHOLD) {
                // Base case: sum directly
                long sum = 0;
                for (int i = start; i < end; i++) sum += arr[i];
                return sum;
            } else {
                // Fork
                int mid = (start + end) / 2;
                SumTask left = new SumTask(arr, start, mid);
                SumTask right = new SumTask(arr, mid, end);

                left.fork();    // run left asynchronously
                long rightResult = right.compute(); // compute right directly
                long leftResult = left.join();  // wait for left

                return leftResult + rightResult;
            }
        }
    }

    static class MyCallable implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            Thread.sleep(1000); // sleeps for a second
            return 1;
        }
    }

    // Create a ThreadLocal holder class
    public class RequestContext {
        private static final ThreadLocal<String> transactionId = new ThreadLocal<>();

        public static void setTransactionId(String id) {
            transactionId.set(id);
        }

        public static String getTransactionId() {
            return transactionId.get();
        }

        public static void clear() {
            transactionId.remove(); // Important to prevent memory leaks
        }
    }

    // Set the transaction ID at the beginning of the request
    // @Component
    public class TransactionIdFilter implements Filter {

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {

            try {
                // Generate or extract transaction ID (e.g., from headers)
                String txnId = UUID.randomUUID().toString();
                RequestContext.setTransactionId(txnId);

                // Continue the chain
                chain.doFilter(request, response);

            } finally {
                // Clean up
                RequestContext.clear();
            }
        }
    }

    static class DatabaseConnectionPool {
        private static final int MAX_CONNECTIONS = 3;
        private final Semaphore semaphore = new Semaphore(MAX_CONNECTIONS, true); // Fair semaphore (i.e. first-come-first-served)

        public void connect() {
            try {
                semaphore.acquire(); // Wait for a permit
                System.out.println(Thread.currentThread().getName() + ": Connected");
                Thread.sleep(2000); // Simulate database work
            } catch (InterruptedException e) { /* handle it */
            } finally {
                System.out.println(Thread.currentThread().getName() + ": Disconnected");
                semaphore.release(); // Release the permit
            }
        }
    }

    static class Service implements Runnable {
        private final String name;
        private final int initTime;
        private final CountDownLatch latch;

        public Service(String name, int initTime, CountDownLatch latch) {
            this.name = name;
            this.initTime = initTime;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(initTime);
                System.out.println(name + " service initialized");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                latch.countDown(); // Decrement count
            }
        }
    }

}
