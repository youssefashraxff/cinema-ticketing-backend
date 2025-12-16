package com.example.cinematicketingbackend.factory;

import com.example.cinematicketingbackend.exception.InvalidCategoryException;
import com.example.cinematicketingbackend.model.MovieCategoryFlyweight;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MovieCategoryFactory {
    private static final Map<String, MovieCategoryFlyweight> categoryCache = new HashMap<>();

    // Initialize predefined categories
    static {
        initializePredefinedCategories();
    }

    private MovieCategoryFactory() {}

    private static void initializePredefinedCategories() {
        // Action category
        categoryCache.put("action", new MovieCategoryFlyweight("action",
            "A fast-paced action film filled with suspense, intense fights, and daring missions.\n   Explosions, chases, and heroic moments drive the story forward.", 12));

        // Horror category
        categoryCache.put("horror", new MovieCategoryFlyweight("horror",
            "A dark and frightening experience that builds tension and fear.\n   Mysterious events and terrifying scenes keep audiences on edge.", 18));

        // Romance category
        categoryCache.put("romance", new MovieCategoryFlyweight("romance",
            "An emotional love story exploring deep relationships and passion.\n   The film focuses on romance, connection, and heartfelt moments.", 18));

        // Comedy category
        categoryCache.put("comedy", new MovieCategoryFlyweight("comedy",
            "A light-hearted and entertaining film full of humor and fun situations.\n   Designed to make audiences laugh and enjoy cheerful moments.", 7));

        // Drama category
        categoryCache.put("drama", new MovieCategoryFlyweight("drama",
            "A story-driven film focusing on emotional depth and character development.\n   It explores real-life challenges and meaningful human experiences.", 7));

        // Cartoon category
        categoryCache.put("cartoon", new MovieCategoryFlyweight("cartoon",
            "A colorful animated adventure suitable for families and children.\n   Fun characters and imaginative storytelling create an enjoyable experience.", 7));
    }

    public static MovieCategoryFlyweight getMovieCategory(String type, String description, int ageRestriction) {
        if (type == null || type.isEmpty()) {
            throw new InvalidCategoryException("Category type cannot be null or empty");
        }

        String normalizedType = type.toLowerCase();

        // Check if category already exists in cache (predefined categories)
        if (categoryCache.containsKey(normalizedType)) {
            return categoryCache.get(normalizedType);
        }

        // For predefined categories, enforce the fixed values
        throw new InvalidCategoryException("Category type '" + type + "' is not a valid predefined category. " +
            "Valid categories are: action, horror, romance, comedy, drama, cartoon");
    }

    public static MovieCategoryFlyweight getMovieCategory(String type) {
        if (type == null || type.isEmpty()) {
            throw new InvalidCategoryException("Category type cannot be null or empty");
        }

        String normalizedType = type.toLowerCase();

        // Check if category exists in cache
        if (categoryCache.containsKey(normalizedType)) {
            return categoryCache.get(normalizedType);
        }

        throw new InvalidCategoryException("Category type '" + type + "' does not exist. " +
            "Valid categories are: action, horror, romance, comedy, drama, cartoon");
    }

   
    public static MovieCategoryFlyweight getExistingCategory(String type) {
        if (type == null || type.isEmpty()) {
            return null;
        }
        return categoryCache.get(type);
    }

   
    public static Set<String> getAllCategoryTypes() {
        return categoryCache.keySet();
    }

    // public static void clearCache() {
    //     categoryCache.clear();  
    // }

    // public static int getCacheSize() {
    //     return categoryCache.size();
    // }
}

