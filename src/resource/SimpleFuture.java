package resource;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

public class SimpleFuture<V> implements Future<V> {
    private volatile V result;
    private volatile boolean isDone = false;
    private volatile boolean isCancelled = false;

    @Override
    public synchronized V get() throws InterruptedException, ExecutionException {
        while (!isDone) {
            wait();
        }
        if (isCancelled) {
            throw new CancellationException();
        }
        return result;
    }

    @Override
    public synchronized boolean isDone() {
        return isDone;
    }

    @Override
    public synchronized boolean cancel(boolean mayInterruptIfRunning) {
        if (isDone) {
            return false;
        }
        isCancelled = true;
        if (mayInterruptIfRunning) {
            // Add logic to interrupt the running thread if needed
        }
        notifyAll();
        return true;
    }

    public synchronized void setResult(V result) {
        this.result = result;
        isDone = true;
        notifyAll();
    }
}