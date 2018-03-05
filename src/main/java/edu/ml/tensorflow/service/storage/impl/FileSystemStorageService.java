package edu.ml.tensorflow.service.storage.impl;

import edu.ml.tensorflow.ApplicationProperties;
import edu.ml.tensorflow.service.storage.StorageService;
import edu.ml.tensorflow.service.storage.exception.StorageException;
import edu.ml.tensorflow.service.storage.exception.StorageFileNotFoundException;
import groovy.lang.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@Singleton
@EnableScheduling
public class FileSystemStorageService implements StorageService {
    private final static Logger LOGGER = LoggerFactory.getLogger(FileSystemStorageService.class);
    private final Path uploadLocation;
    private final Path predictedLocation;

    @Autowired
    public FileSystemStorageService(final ApplicationProperties applicationProperties) {
        this.uploadLocation = Paths.get(applicationProperties.getUploadDir());
        this.predictedLocation = Paths.get(applicationProperties.getOutputDir());
        cleanUpFolders();
    }

    @Override
    public String store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException("Cannot store file with relative path outside current directory " + filename);
            }
            String newFileName = UUID.randomUUID() + ".jpg";
            Files.copy(file.getInputStream(), this.uploadLocation.resolve(newFileName),
                    StandardCopyOption.REPLACE_EXISTING);
            return newFileName;
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.uploadLocation, 1)
                    .filter(path -> !path.equals(this.uploadLocation))
                    .map(path -> this.uploadLocation.relativize(path));
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return uploadLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(uploadLocation.toFile());
        FileSystemUtils.deleteRecursively(predictedLocation.toFile());
        LOGGER.info("Target folders cleaned up at: {}", LocalDateTime.now());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(uploadLocation);
            Files.createDirectories(predictedLocation);
            LOGGER.info("Target folders initialized at: {}", LocalDateTime.now());
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Scheduled(fixedRate = 1000 * 3600)
    private void cleanUpFolders() {
        deleteAll();
        init();
    }
}
