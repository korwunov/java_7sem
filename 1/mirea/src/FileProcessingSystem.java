import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

class File {
    private String fileType;
    private int fileSize;

    public File(String fileType, int fileSize) {
        this.fileType = fileType;
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public int getFileSize() {
        return fileSize;
    }

    @Override
    public String toString() {
        return "File {" +
                "fileType='" + fileType + '\'' +
                ", fileSize=" + fileSize +
                '}';
    }
}

class FileGenerator implements Runnable {
    private final BlockingQueue<File> queue;
    private final Random random = new Random();

    public FileGenerator(BlockingQueue<File> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String[] fileTypes = {"XML", "JSON", "XLS"};
                String fileType = fileTypes[random.nextInt(3)];
                int fileSize = random.nextInt(91) + 10;
                File file = new File(fileType, fileSize);

                queue.put(file);
                System.out.println("Generated: " + file);

                Thread.sleep(random.nextInt(901) + 100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}

class FileProcessor implements Runnable {
    private final BlockingQueue<File> queue;
    private final String fileType;

    public FileProcessor(BlockingQueue<File> queue, String fileType) {
        this.queue = queue;
        this.fileType = fileType;
    }

    @Override
    public void run() {
        while (true) {
            try {
                File file = queue.take();
                processFile(file);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void processFile(File file) {
        if (file.getFileType().equals(fileType)) {
            try {
                int processingTime = file.getFileSize() * 7;
                Thread.sleep(processingTime);
                System.out.println("Processed (" + fileType + "): " + file);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } else {
            System.out.println("File type not supported (Processor " + fileType + "): " + file);
        }
    }
}

public class FileProcessingSystem {
    public static void main(String[] args) {
        BlockingQueue<File> queue = new LinkedBlockingQueue<>(5);

        Thread generatorThread = new Thread(new FileGenerator(queue));
        generatorThread.start();

        // обработчики файлов разных типов
        Thread xmlProcessor = new Thread(new FileProcessor(queue, "XML"));
        Thread jsonProcessor = new Thread(new FileProcessor(queue, "JSON"));
        Thread xlsProcessor = new Thread(new FileProcessor(queue, "XLS"));
        
        xmlProcessor.start();
        jsonProcessor.start();
        xlsProcessor.start();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        generatorThread.interrupt();
        xmlProcessor.interrupt();
        jsonProcessor.interrupt();
        xlsProcessor.interrupt();

        try {
            generatorThread.join();
            xmlProcessor.join();
            jsonProcessor.join();
            xlsProcessor.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("File processing system shut down.");
    }
}