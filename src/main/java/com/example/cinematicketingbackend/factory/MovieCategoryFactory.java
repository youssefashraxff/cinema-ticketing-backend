package com.example.cinematicketingbackend.factory;

import com.example.cinematicketingbackend.exception.InvalidCategoryException;
import com.example.cinematicketingbackend.model.MovieCategoryFlyweight;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MovieCategoryFactory {
    private static final Map<String, MovieCategoryFlyweight> categoryCache = new HashMap<>();


    private MovieCategoryFactory() {}

    public static MovieCategoryFlyweight getMovieCategory(String type, String description, int ageRestriction) {
        if (type == null || type.isEmpty()) {
            throw new InvalidCategoryException("Category type cannot be null or empty");
        }

        String normalizedType = type;

        // Check if category already exists in cache
        if (categoryCache.containsKey(normalizedType)) {
           
            // Return existing flyweight (ignore new description/age if different)
            return categoryCache.get(normalizedType);
        }

        // Create new flyweight and add to cache
        MovieCategoryFlyweight flyweight = new MovieCategoryFlyweight(normalizedType, description, ageRestriction);
        categoryCache.put(normalizedType, flyweight);
        return flyweight;
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

