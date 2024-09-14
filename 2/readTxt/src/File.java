import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class File {
    public String FILENAME;

    public File(String fileName) { this.FILENAME = fileName; }

    public void writeToFile() {
        try {
            FileWriter file = new FileWriter(this.FILENAME);
            file.write("Тестовая строка для записи в файл \n А вот тут должна быть новая строка (второй элемент в массиве для чтения)");
            file.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String readFile() {
        Path filePath = Paths.get(this.FILENAME);
        try {
            List<String> fileLines = Files.readAllLines(filePath);
            return fileLines.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
