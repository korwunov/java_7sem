
import java.util.Random;
import java.util.concurrent.*;

public class ArrayOperations {
    private static final int SIZE = 10000;
    private static final int[] array = new int[SIZE];
    private static final Random random = new Random();

    static {
        // Заполнение массива случайными числами
        for (int i = 0; i < SIZE; i++) {
            array[i] = random.nextInt(100); // Случайные числа от 0 до 99
        }
    }

    // Последовательный поиск суммы
    public static int sumSequential() throws InterruptedException {
        int sum = 0;
        for (int value : array) {
            sum += value;
            Thread.sleep(1);
        }
        return sum;
    }

    // Последовательный поиск максимального элемента
    public static int maxSequential() throws InterruptedException {
        int max = Integer.MIN_VALUE;
        for (int value : array) {
            if (value > max) {
                max = value;
            }
            Thread.sleep(1);
        }
        return max;
    }

    // Поиск суммы с использованием многопоточности
    public static int sumConcurrent() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        int chunkSize = SIZE / 4;
        Future<Integer>[] futures = new Future[4];

        for (int i = 0; i < 4; i++) {
            final int start = i * chunkSize;
            final int end = (i + 1) * chunkSize;

            futures[i] = executor.submit(() -> {
                int sum = 0;
                for (int j = start; j < end; j++) {
                    sum += array[j];
                    Thread.sleep(1);
                }
                return sum;
            });
        }

        int totalSum = 0;
        for (Future<Integer> future : futures) {
            totalSum += future.get();
        }

        executor.shutdown();
        return totalSum;
    }

    // Поиск максимального элемента с использованием многопоточности
    public static Integer maxConcurrent() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        int chunkSize = SIZE / 4;
        Future<Integer>[] futures = new Future[4];

        for (int i = 0; i < 4; i++) {
            final int start = i * chunkSize;
            final int end = (i + 1) * chunkSize;

            futures[i] = executor.submit(() -> {
                int max = Integer.MIN_VALUE;
                for (int j = start; j < end; j++) {
                    if (array[j] > max) {
                        max = array[j];
                    }
                    Thread.sleep(1);
                }
                return max;
            });
        }

        int totalMax = Integer.MIN_VALUE;
        for (Future<Integer> future : futures) {
            totalMax = Math.max(totalMax, future.get());
        }

        executor.shutdown();
        return totalMax;
    }

    // Поиск суммы с использованием ForkJoin
    public static int sumForkJoin() throws InterruptedException, ExecutionException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new SumTask(0, SIZE));
    }

    // Поиск максимального элемента с использованием ForkJoin
    public static int maxForkJoin() throws InterruptedException, ExecutionException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new MaxTask(0, SIZE));
    }

    static class SumTask extends RecursiveTask<Integer> {
        private final int start;
        private final int end;

        SumTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if (end - start <= 1000) {
                int sum = 0;
                for (int i = start; i < end; i++) {
                    sum += array[i];
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                return sum;
            } else {
                int mid = (start + end) / 2;
                SumTask leftTask = new SumTask(start, mid);
                SumTask rightTask = new SumTask(mid, end);
                leftTask.fork();
                return rightTask.compute() + leftTask.join();
            }
        }
    }

    static class MaxTask extends RecursiveTask<Integer> {
        private final int start;
        private final int end;

        MaxTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if (end - start <= 1000) {
                int max = Integer.MIN_VALUE;
                for (int i = start; i < end; i++) {
                    if (array[i] > max) {
                        max = array[i];
                    }
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                return max;
            } else {
                int mid = (start + end) / 2;
                MaxTask leftTask = new MaxTask(start, mid);
                MaxTask rightTask = new MaxTask(mid, end);
                leftTask.fork();
                return Math.max(rightTask.compute(), leftTask.join());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long startTime, endTime;

        // Последовательный поиск суммы
        startTime = System.currentTimeMillis();
        int sequentialSum = sumSequential();
        endTime = System.currentTimeMillis();
        System.out.println("Sequential Sum: " + sequentialSum + ", Time: " + (endTime - startTime) + " ms");

        // Последовательный поиск максимума
        startTime = System.currentTimeMillis();
        int sequentialMax = maxSequential();
        endTime = System.currentTimeMillis();
        System.out.println("Sequential Max: " + sequentialMax + ", Time: " + (endTime - startTime) + " ms");

        // Поиск суммы с использованием многопоточности
        startTime = System.currentTimeMillis();
        int concurrentSum = sumConcurrent();
        endTime = System.currentTimeMillis();
        System.out.println("Concurrent Sum: " + concurrentSum + ", Time: " + (endTime - startTime) + " ms");

        // Поиск максимума с использованием многопоточности
        startTime = System.currentTimeMillis();
        int concurrentMax = maxConcurrent();
        endTime = System.currentTimeMillis();
        System.out.println("Concurrent Max: " + concurrentMax + ", Time: " + (endTime - startTime) + " ms");

        // Поиск суммы с использованием ForkJoin
        startTime = System.currentTimeMillis();
        int forkJoinSum = sumForkJoin();
        endTime = System.currentTimeMillis();
        System.out.println("ForkJoin Sum: " + forkJoinSum + ", Time: " + (endTime - startTime) + " ms");

        // Поиск максимума с использованием ForkJoin
        startTime = System.currentTimeMillis();
        int forkJoinMax = maxForkJoin();
        endTime = System.currentTimeMillis();
        System.out.println("ForkJoin Max: " + forkJoinMax + ", Time: " + (endTime - startTime) + " ms");
    }
}