import java.io.*;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ExecutionException;





public class Main {

    public static LinkedList<String> loadDataFromFile(String path){
        File file = new File(path);
        LinkedList<String> ans = new LinkedList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                ans.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            return ans;
        }
    }

    public static void saveDataToFile(String path, LinkedList<String> data){
        try {
            FileWriter writer = new FileWriter(path, true);
            for (String msg: data
                 ) {
                writer.write(msg);
                writer.write("\r\n");   // write new line
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {



        LinkedList<String> data = new LinkedList<>();




        // Load data saved to file
        /*
        String path = "src/resource/queue_file.txt";
        data = loadDataFromFile(path);
        */


        // declare new queue;
        Queue q = new Queue(data);


        // First scenario
        /*
        data = new LinkedList<>();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println((q.get_msg()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(50000);
                    q.send_msg("first msg in");
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t2.start();

        */


        // Second scenario
        // get message without blocking

        /*

        data = new LinkedList<>();
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Optional<String> result = q.get_msg_nb();
                    if(result.isPresent()) {
                        System.out.println(result.get());
                    } else {
                        System.out.println("no item in queue");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        t3.start();

        */



        // Third scenario

        /*


        Runnable consumerRun = new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("consumer " + Thread.currentThread() +  " in");
                    System.out.println((q.get_msg()));
                    System.out.println("consumer " + Thread.currentThread() + " out");
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable producerRun =  new Runnable() {
            Random random = new Random();
            @Override
            public void run() {
                try {
                    System.out.println("producer " + Thread.currentThread() +  " in");
                    q.send_msg("message num " + random.nextInt(1000));
                    System.out.println("producer " + Thread.currentThread() +  " out");
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };

        int t = 0;
        while (t++ < 5) {
            new Thread(consumerRun).start();
            new Thread(producerRun).start();
            Thread.sleep(1000);
        }

        */

        // get stats

        /*

        // send message to queue
        q.send_msg("a");
        q.send_msg("b");
        q.send_msg("c");
        q.send_msg("sag");
        // get oldest message in queue
        System.out.println(q.get_msg());

        // get message without blocking
        Optional<String> result = q.get_msg_nb();
        if(result.isPresent()) {
            System.out.println(result.get());
        } else {
            System.out.println("no item in queue");
        }

        // get queue stats
        StatsResult stat = q.stats();
        System.out.println(stat.getMessageCount());
        System.out.println(stat.getTotalMessageLength());
        System.out.println(stat.getTotalProgramMemoryUsage());

        */






        //Fourth scenario

        /*

        Runnable producerRun =  new Runnable() {
            Random random = new Random();
            @Override
            public void run() {
                try {
                    System.out.println("producer " + Thread.currentThread() +  " in");
                    q.send_msg("message num " + random.nextInt(1000));
                    System.out.println("producer " + Thread.currentThread() +  " out");
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };


        int t = 0;
        while (t < 4) {
            new Thread(producerRun).start();
            t++ ;
        }


         */


        //Fifth scenario

        /*


        Queue q = new Queue(data, 2, 5);

        Runnable producerRun =  new Runnable() {
            Random random = new Random();
            @Override
            public void run() {
                try {
                    System.out.println("producer " + Thread.currentThread() +  " in");
                    q.send_msg("message_too_long");
                    System.out.println("producer " + Thread.currentThread() +  " out");
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(producerRun).start();

         */


        // Save data to file
        /*saveDataToFile(path, data);*/

    }
}
