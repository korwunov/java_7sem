package org.fileObserver;

public class FileProcessingSystem {
    public static void main(String[] args) {
        int queueCapacity = 5;
        FileQueue queue = new FileQueue(queueCapacity);

        String[] supportedFileTypes = {"JSON", "XML", "XLSX"};
        for (String fileType : supportedFileTypes) {
            new FileProcessor(fileType).processFiles(queue.getFileObservable())
                    .subscribe(() -> {},
                            throwable -> System.err.println("Ошибка обработки файла: " + throwable)
                    );
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}