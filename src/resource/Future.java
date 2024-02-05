package resource;

import java.util.concurrent.ExecutionException;

public interface Future<T> {
    T get() throws InterruptedException, ExecutionException;
    boolean isDone();
    boolean cancel(boolean mayInterruptIfRunning);
}
