package com.example.cinematicketingbackend.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileHandlerManager {
    private static FileHandlerManager instance;
    private final ObjectMapper objectMapper;

    private FileHandlerManager() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public static synchronized FileHandlerManager getInstance() {
        if (instance == null) {
            instance = new FileHandlerManager();
        }
        return instance;
    }

    // Generic method to read list from JSON
    public <T> List<T> readList(Path filePath, TypeReference<List<T>> typeReference) throws Exception {
        if (!Files.exists(filePath)) {
            // Create empty file if it doesn't exist
            Files.createDirectories(filePath.getParent());
            Files.writeString(filePath, "[]");
            return new ArrayList<>();
        }

        String content = Files.readString(filePath);
        if (content == null || content.trim().isEmpty()) {
            return new ArrayList<>();
        }

        return objectMapper.readValue(content, typeReference);
    }

    // Overloaded method for reading with Class parameter
    public <T> List<T> readList(Path filePath, Class<T> clazz) throws Exception {
        return objectMapper.readValue(Files.exists(filePath) ? Files.readString(filePath) : "[]", 
                                    objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
    }

    // Write list to JSON
    public <T> void write(Path filePath, List<T> list) throws Exception {
        if (!Files.exists(filePath)) {
            Files.createDirectories(filePath.getParent());
        }
        
        String json = objectMapper.writeValueAsString(list);
        Files.writeString(filePath, json);
    }

    // Write single object to JSON
    public <T> void write(Path filePath, T object) throws Exception {
        if (!Files.exists(filePath)) {
            Files.createDirectories(filePath.getParent());
        }
        
        String json = objectMapper.writeValueAsString(object);
        Files.writeString(filePath, json);
    }
}