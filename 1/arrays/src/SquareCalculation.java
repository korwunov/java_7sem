import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class SquareCalculation {
    private static final ExecutorService executor = Executors.newCachedThreadPool();
    private static final Random random = new Random();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Введите число для возведения в квадрат (или 'exit' для выхода): ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            try {
                int number = Integer.parseInt(input);
                // Отправляем задачу на выполнение
                Future<Integer> future = executor.submit(() -> {
                    // Имитация обработки запроса с задержкой от 1 до 5 секунд
                    int delay = 1 + random.nextInt(5); // случайная задержка от 1 до 5 секунд
                    TimeUnit.SECONDS.sleep(delay);
                    return number * number; // Возвращаем квадрат числа
                });

                // Получаем результат (это будет блокироваться до завершения задачи)
                Integer result = future.get();
                System.out.println("Результат: " + result);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите корректное число.");
            } catch (Exception e) {
                System.out.println("Произошла ошибка: " + e.getMessage());
            }
        }

        System.out.println("Завершение работы программы.");
        executor.shutdown(); // Ожидаем завершения всех задач
    }
}