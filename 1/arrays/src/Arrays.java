import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.time.*;
import java.util.concurrent.*;

public class Arrays {
    public List<Integer> array = new ArrayList<>();
    Random rand = new Random();

    public Arrays() {
        for (int i = 0; i < 10000; i++) {
            this.array.add(rand.nextInt(100));
        }
    }

    /**
     * Задание 1. Метод последовательной обработки
     */
    public int getSumConsistently() {
        LocalTime startTime = LocalTime.now();
        int sum = 0;
        for (int num : this.array) {
            sum += num;
        }
        LocalTime endTime = LocalTime.now();
        System.out.println("Время последовательной обработки массива: " +
                Math.abs(endTime.getNano() - startTime.getNano()));
        return sum;
    }

    /**
     * Задание 2. Метод многопоточной обработки с Future
     */
    public int getSumInSubarray(List<Integer> array) {
        int sum = 0;
        for (int num : array) {
            sum += num;
        }
        return sum;
    }

    public int getSumThread() throws InterruptedException, ExecutionException {
        LocalTime startTime = LocalTime.now();
        int sum = 0;
        int threadsNumber = Runtime.getRuntime().availableProcessors();
        ExecutorService service = Executors.newFixedThreadPool(threadsNumber);
        List<Callable<Integer>> tasks = new ArrayList<>();
        int batchSize = this.array.size() / threadsNumber;

        for (int i = 0; i < threadsNumber; i++) {
            int startIndex = i * batchSize;
            int endIndex = (i + 1) * batchSize;
            tasks.add(() -> getSumInSubarray(this.array.subList(startIndex, endIndex)));
        }
        List<Future<Integer>> futures = service.invokeAll(tasks);

        for (Future<Integer> threadResult : futures) {
            int batchSum = threadResult.get();
            sum += batchSum;
        }
        LocalTime endTime = LocalTime.now();
        System.out.println("Время многопоточной обработки массива: " +
                Math.abs(endTime.getNano() - startTime.getNano()));
        service.shutdown();
        return sum;
    }

    /**
     * Задание 3. Метод многопоточной обработки с ForkJoin
     */

    public int FindSumTask extends
}

