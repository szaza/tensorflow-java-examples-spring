package edu.ml.tensorflow.service.api;

import edu.ml.tensorflow.classifier.ObjectDetector;
import edu.ml.tensorflow.service.exception.ServiceException;
import edu.ml.tensorflow.service.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class FileUploadController {
    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {
        return "upload-image";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        String originalImage = "/upload-dir/" + storageService.store(file);
        String predictedImage = predict(originalImage);
        model.addAttribute("originalName", file.getOriginalFilename());
        model.addAttribute("originalImage", originalImage);
        model.addAttribute("predictedImage", predictedImage);
        return "display-result";
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> handleStorageFileNotFound(ServiceException ex) {
        return ResponseEntity.notFound().build();
    }

    private String predict(final String fileName) {
        ObjectDetector objectDetector = new ObjectDetector();
        return objectDetector.detect("." + fileName);
    }
}
