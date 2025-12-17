package com.example.cinematicketingbackend.repository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FileManager {

    private static FileManager instance;
    private static final ObjectMapper mapper = new ObjectMapper();

    private FileManager() {}

    public static FileManager getInstance() {
        if (instance == null) {
            instance = new FileManager();
        }
        return instance;
    }

    private Path resolvePath(String filePath) {
        return Paths.get(System.getProperty("user.dir")).resolve(filePath);
    }

    public <T> T read(String filePath, TypeReference<T> type) {
        Path path = resolvePath(filePath);
        System.out.println("File path: "+path);
        try {
            // Path path = resolvePath(filePath);

            if (!Files.exists(path)) {
                System.out.println("File not found");
                return null;
            }
            System.out.println("File manager: "+mapper.readValue(path.toFile(), type));
            return mapper.readValue(path.toFile(), type);
        } catch (Exception e) {
            System.out.println("Failed to read file: " + filePath);
            throw new RuntimeException(e);
        }
    }

    public void write(String filePath, Object data) {
        try {
            Path path = resolvePath(filePath);
            mapper.writerWithDefaultPrettyPrinter()
                  .writeValue(path.toFile(), data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to write file: " + filePath, e);
        }
    }
}