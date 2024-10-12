import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirectoryListener {
    private Map<Path, List<String>> filesContent = new HashMap<>();
    private Map<Path, String> filesHashes = new HashMap<>();

    public DirectoryListener() throws IOException, InterruptedException {
        Path directoryToListen = Paths.get("./exampleDir");
        WatchService dirListener = FileSystems.getDefault().newWatchService();

        directoryToListen.register(dirListener,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_DELETE
        );

        getInitialDirectoryState(directoryToListen);

        WatchKey key = dirListener.take();
        while (true) {
            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> eventType = event.kind();
                Path filePath = directoryToListen.resolve((Path) event.context());
                if (eventType == StandardWatchEventKinds.ENTRY_CREATE) {
                    System.out.println("Создан новый файл: " + filePath);
                    filesContent.put(filePath, readFileData(filePath));
                    filesHashes.put(filePath, getFileHashsum(filePath));
                }
                else if (eventType == StandardWatchEventKinds.ENTRY_MODIFY) {
                    System.out.println("Файл " + filePath + " изменен");
                    this.getFileChanges(filePath);
                }
                else if (eventType == StandardWatchEventKinds.ENTRY_DELETE) {
                    System.out.println("Удален файл " + filePath);
                    String hash = this.filesHashes.get(filePath);
                    if (hash != null) {
                        System.out.println("Хэш-сумма удаленного файла: " + hash);
                    }
                }
            }
        }
    }

    private void getFileChanges(Path filePath) throws IOException {
        List<String> newContent = readFileData(filePath);
        List<String> prevContent = this.filesContent.get(filePath);

        if (prevContent != null) {
            List<String> newLines = newContent.stream().filter(
                    line -> !prevContent.contains(line)
            ).toList();

            List<String> deletedLines = prevContent.stream().filter(
                    line -> !newContent.contains(line)
            ).toList();

            if (!newLines.isEmpty()) {
                System.out.println("\tДобавленные строки: ");
                newLines.forEach(line -> System.out.println("\t\t" + line));
            }
            if (!deletedLines.isEmpty()) {
                System.out.println("\tУдаленные строки: ");
                deletedLines.forEach(line -> System.out.println("\t\t" + line));
            }
        }
        this.filesContent.put(filePath, newContent);
        this.filesHashes.put(filePath, getFileHashsum(filePath));
    }

    private void getInitialDirectoryState(Path dir) {
        try {
            DirectoryStream<Path> stream = Files.newDirectoryStream(dir);
            for (Path filePath : stream) {
                if (Files.isRegularFile(filePath)) {
                    this.filesContent.put(filePath, readFileData(filePath));
                    this.filesHashes.put(filePath, getFileHashsum(filePath));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> readFileData(Path filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

//      Изначально делалось под сверху хэш сумм одного и того же файла во время действия, потом понял, что наверное это лишнее
//    private List<String> getListWithFileHashsums(Path filePath) throws IOException {
//        List<String> fileHashes = this.filesHashes.get(filePath);
//        if (fileHashes == null) {
//            fileHashes = List.of(getFileHashsum(filePath));
//        } else {
//            fileHashes.add(getFileHashsum(filePath));
//        }
//        return fileHashes;
//    }

    private static String getFileHashsum(Path filePath) throws IOException {
        InputStream inputStream = Files.newInputStream(filePath);
        StringBuilder builder = new StringBuilder();
        byte[] bytes = inputStream.readAllBytes();
        for (byte b: bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
