package com.example.cinematicketingbackend.patterns;

import com.example.cinematicketingbackend.exception.InvalidCategoryException;
import com.example.cinematicketingbackend.model.MovieCategoryFlyweight;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MovieCategoryFlyweigth {
    private static final Map<String, MovieCategoryFlyweight> categoryCache = new HashMap<>();

    // Initialize predefined categories
    static {
        initializePredefinedCategories();
    }

    private MovieCategoryFlyweigth() {}

    private static void initializePredefinedCategories() {
        // Action category
        categoryCache.put("action", new MovieCategoryFlyweight("action", 12));

        // Horror category
        categoryCache.put("horror", new MovieCategoryFlyweight("horror", 18));

        // Romance category
        categoryCache.put("romance", new MovieCategoryFlyweight("romance",18));

        // Comedy category
        categoryCache.put("comedy", new MovieCategoryFlyweight("comedy", 7));

        // Drama category
        categoryCache.put("drama", new MovieCategoryFlyweight("drama", 7));

        // Cartoon category
        categoryCache.put("cartoon", new MovieCategoryFlyweight("cartoon",  7));
    }

    public static MovieCategoryFlyweight getMovieCategory(String type, int ageRestriction) {
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
}

