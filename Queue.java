import resource.SimpleFuture;

import javax.xml.stream.FactoryConfigurationError;
import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TransferQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Queue  {

    private final LinkedList<String> queue;
    private final Semaphore writer = new Semaphore(1);
    private final Semaphore reader = new Semaphore(1);
    private final Semaphore readWrite = new Semaphore(0);
    private int maxLimit = 0;
    private int maxMessagesLength = 0;
    private AtomicInteger queueLength = new AtomicInteger(0);



    private final long byteToMegabytes = 1024L * 1024L;

    private long beforeUsedMem;

    public Queue(LinkedList<String> queue) {
        this.queue = queue;
        this.queueLength.set(queue.size());
        this.beforeUsedMem = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
    }

    public Queue(LinkedList<String> queue, int maxLimit) {
        this.queue = queue;
        this.maxLimit = maxLimit;
        this.queueLength.set(queue.size());
        this.beforeUsedMem = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
    }

    public Queue(LinkedList<String> queue, int maxLimit, int maxMessagesLength) {
        this.queue = queue;
        this.maxLimit = maxLimit;
        this.queueLength.set(queue.size());
        this.beforeUsedMem = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        this.maxMessagesLength = maxMessagesLength;
    }



    public String get_msg() throws InterruptedException, ExecutionException {
        SimpleFuture<String> future = new SimpleFuture<>();
        new Thread(() -> {
            /*
            while (queue.isEmpty()) {
                System.out.println("Waiting for data...");
            }

             */

            String item = null;
            try {
                if(queue.isEmpty()){
                    System.out.println("Waiting for data...");
                }
                readWrite.acquire(); // added to wait for msg to come in queue
                reader.acquire(1);
                item = queue.remove();
                queueLength.decrementAndGet();
                future.setResult(item);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                reader.release(1);
            }
        }).start();
        return future.get();
    }




    public Optional<String> get_msg_nb() throws ExecutionException, InterruptedException {
        if(queueLength.get() > 0) { // value exists in queue --- could have problems
            return Optional.ofNullable(get_msg());
        } else { // value not exists in queue
            return Optional.empty();
        }
    }

    public boolean send_msg(final String item) throws ExecutionException, InterruptedException {
        SimpleFuture<Boolean> future = new SimpleFuture<>();
        new Thread(() -> {

            try {
                writer.acquire();
                if (item.length() >= maxMessagesLength){
                    System.out.println("Word length limit exceeded");
                    future.setResult(false);
                }
                if (getMaxLimit() != 0){
                /*
                while(queue.size() == maxLimit) { // find a better way to deal with this
                    // busy wait
                }
                 */


                    if (queueLength.get() == getMaxLimit()){
                        System.out.println("Queue is full");
                        future.setResult(false);
                    }

                }
                queue.add(item);
                queueLength.incrementAndGet();
                readWrite.release(); //added to bring out reading from wait
                future.setResult(true);
//                System.out.println(queue.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                writer.release();
            }
        }).start();
        return future.get();
    }



    public synchronized StatsResult stats(){
        long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        int messageCount = queueLength.get();
        int countLength = 0;
        for (String msg: queue
             ) {
            countLength += msg.length();
        }
        return new StatsResult(messageCount, countLength, (int) ((afterUsedMem - getBeforeUsedMem()) / byteToMegabytes));
    }


    public int getMaxLimit() {
        return maxLimit;
    }

    public long getBeforeUsedMem() {
        return beforeUsedMem;
    }
}
