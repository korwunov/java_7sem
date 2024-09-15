import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.*;

public class CopyService {
    public static final String ORIGIN_FILENAME = "OneHundredMbFile.txt";
    public static final String TARGET_FILENAME = "SecondFile.txt";
    /**
     * Генерит файл размером 100мб в корне проекта
     */
    public CopyService() throws IOException {
        RandomAccessFile f = new RandomAccessFile(ORIGIN_FILENAME, "rw");
        f.setLength(1024 * 1024 * 100);
    }

    public void deleteCopiedFile() {
        new File(TARGET_FILENAME).delete();
    }

    /**
     * Копирует файл при помощи Input/OutputStream
     */
    public void copyFilesByStreams() throws IOException {
        long startTime = System.currentTimeMillis();
        InputStream inputStream = new FileInputStream(ORIGIN_FILENAME);
        OutputStream outputStream = new FileOutputStream(TARGET_FILENAME);
        inputStream.transferTo(outputStream);
        inputStream.close();
        outputStream.close();
        long endTime = System.currentTimeMillis();
        Runtime runtime = Runtime.getRuntime();
        long memoryUsed = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Копирование при помощи Input/OutputStream: " +
                "\n\t ⏱️ Затраченное время " + (endTime - startTime) + " МС" +
                "\n\t 💾 Использованная память " + memoryUsed / (1024 * 1024) + " MB");
    }

    /**
     * Копирует файл при помощи FileChannels
     */
    public void copyFilesByChannels() throws IOException {
        long startTime = System.currentTimeMillis();
        FileChannel originFile = new FileInputStream(ORIGIN_FILENAME).getChannel();
        FileChannel targetFile = new FileOutputStream(TARGET_FILENAME).getChannel();
        originFile.transferTo(0, originFile.size(), targetFile);
        originFile.close();
        targetFile.close();
        long endTime = System.currentTimeMillis();
        Runtime runtime = Runtime.getRuntime();
        long memoryUsed = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Копирование при помощи Channels: " +
                "\n\t ⏱️ Затраченное время " + (endTime - startTime) + " МС" +
                "\n\t 💾 Использованная память " + memoryUsed / (1024 * 1024) + " MB");
    }

    /**
     * Копирует файл при помощи Apache Commons IO
     */
    public void copyFileByApacheIo() throws IOException {
        long startTime = System.currentTimeMillis();
        File originFile = new File(ORIGIN_FILENAME);
        File targetFile = new File(TARGET_FILENAME);
        FileUtils.copyFile(originFile, targetFile);
        long endTime = System.currentTimeMillis();
        Runtime runtime = Runtime.getRuntime();
        long memoryUsed = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Копирование при помощи Apache IO: " +
                "\n\t ⏱️ Затраченное время " + (endTime - startTime) + " МС" +
                "\n\t 💾 Использованная память " + memoryUsed / (1024 * 1024) + " MB");
    }

    /**
     * Копирует файл при помощи File
     */
    public void copyFileByFiles() throws IOException {
        long startTime = System.currentTimeMillis();
        Path originPath = Paths.get(ORIGIN_FILENAME);
        Path targetPath = Paths.get(TARGET_FILENAME);
        Files.copy(originPath, targetPath);
        long endTime = System.currentTimeMillis();
        Runtime runtime = Runtime.getRuntime();
        long memoryUsed = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Копирование при помощи Files: " +
                "\n\t ⏱️ Затраченное время " + (endTime - startTime) + " МС" +
                "\n\t 💾 Использованная память " + memoryUsed / (1024 * 1024) + " MB");
    }
}
