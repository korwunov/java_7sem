package mirea.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import mirea.service.FilestoreService;


@RestController
@RequestMapping("/file")
public class ExportController {

    @Autowired
    FilestoreService fileService;

    @GetMapping("/download/{mainStorage}/{filename}")
    public HttpEntity<byte[]> downloadFile(@PathVariable String mainStorage,
                                           @PathVariable String filename) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set(
                "Content-Disposition", "attachment; filename=\"" + filename +"\""
        );

        String path = "./" + mainStorage + "/" + filename;
        return new HttpEntity<>(fileService.get(path), headers);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        fileService.create(file);
        return ResponseEntity.status(HttpStatus.OK).body("Файл загружен успешно");
    }
}