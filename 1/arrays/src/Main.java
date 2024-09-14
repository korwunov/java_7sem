import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Arrays arrs = new Arrays();
        System.out.println(arrs.getSumConsistently());
        System.out.println(arrs.getSumThread());
    }
}
