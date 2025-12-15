package com.example.cinematicketingbackend.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


@Component
public class FileHandlerManager {
    private static volatile FileHandlerManager instance;
    private final ObjectMapper objectMapper;
    private final ReadWriteLock lock;

    private FileHandlerManager() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.lock = new ReentrantReadWriteLock();
    }

    public static FileHandlerManager getInstance() {
        if (instance == null) {
          instance = new FileHandlerManager();
        }
        return instance;
    }

  
    public <T> T read(Path filePath, Class<T> clazz) {
        lock.readLock().lock();
        try {
            if (!Files.exists(filePath)) {
                // Return default instance for array types or throw exception for single objects
                if (clazz.isArray()) {
                    return clazz.cast(java.lang.reflect.Array.newInstance(clazz.getComponentType(), 0));
                }
                throw new RuntimeException("File not found: " + filePath);
            }

            String content = Files.readString(filePath);
            if (content.trim().isEmpty()) {
                // Return empty array for array types
                if (clazz.isArray()) {
                    return clazz.cast(java.lang.reflect.Array.newInstance(clazz.getComponentType(), 0));
                }
                return null;
            }

            return objectMapper.readValue(content, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + filePath, e);
        } finally {
            lock.readLock().unlock();
        }
    }

 
    public <T> List<T> readList(Path filePath, Class<T> elementType) {
        lock.readLock().lock();
        try {
            if (!Files.exists(filePath)) {
                return new ArrayList<>();
            }

            String content = Files.readString(filePath);
            if (content.trim().isEmpty()) {
                return new ArrayList<>();
            }

            // Use the elementType to create a proper TypeReference
            return objectMapper.readValue(content,
                objectMapper.getTypeFactory().constructCollectionType(List.class, elementType));
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + filePath, e);
        } finally {
            lock.readLock().unlock();
        }
    }

  
    public void write(Path filePath, Object data) {
        lock.writeLock().lock();
        try {
            // Create parent directories if they don't exist
            Files.createDirectories(filePath.getParent());

            String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
            Files.writeString(filePath, jsonString);
        } catch (IOException e) {
            throw new RuntimeException("Error writing file: " + filePath, e);
        } finally {
            lock.writeLock().unlock();
        }
    }
}
