package edu.ml.tensorflow;

import edu.ml.tensorflow.service.storage.StorageProperties;
import edu.ml.tensorflow.service.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class Application {
    //private final static String IMAGE = "/image/eagle.jpg";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
//        ObjectDetector objectDetector = new ObjectDetector();
//        objectDetector.detect(IMAGE);
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };
    }
}
