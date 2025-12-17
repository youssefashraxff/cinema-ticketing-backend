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

    public <T> T read(String filePath, TypeReference<T> type) {
        System.out.println("Working dir = " + System.getProperty("user.dir"));
        System.out.println("Trying to read = " + Paths.get(filePath).toAbsolutePath());
        try {
            Path path = Paths.get(filePath);
            
            if (!Files.exists(path)) {
                return null;
            }
            System.out.println("File exists: " + Files.exists(Paths.get(filePath)));
            System.out.println("File size: " + Files.size(Paths.get(filePath)));
            return mapper.readValue(path.toFile(), type);
        } catch (Exception e) {
            e.printStackTrace();   // 👈 keep this
    throw new RuntimeException(e);  // 👈 wrap the REAL cause
        }
    }

    public void write(String filePath, Object data) {
        try {
            Path path = Paths.get(filePath);
            mapper.writerWithDefaultPrettyPrinter()
                  .writeValue(path.toFile(), data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to write file: " + filePath, e);
        }
    }
}