package mirea.service;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FilestoreService {

    @PostConstruct
    public void init() throws Exception {
        String folderName = "./storage";
        File folder = new File(folderName);

        if (!folder.exists()) {
            boolean success = folder.mkdir();
            if (!success){
                throw new Exception("Не удалось создать директорию.");
            }
        }
    }


    public byte[] get(String path) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            throw new Exception("Файла по данному пути не существует");
        }
        return Files.readAllBytes(file.toPath());
    }

    public String create(MultipartFile file) throws Exception {
        File newFile = new File("./storage/" + file.getOriginalFilename());

        FileOutputStream outputStream = new FileOutputStream(newFile);

        outputStream.write(file.getBytes());
        outputStream.close();

        return file.getOriginalFilename();
    }
}
