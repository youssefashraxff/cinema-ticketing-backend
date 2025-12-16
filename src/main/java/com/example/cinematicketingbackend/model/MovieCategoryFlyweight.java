package com.example.cinematicketingbackend.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MovieCategoryFlyweight {
    private final String type; 
    private final String descriptionOfMovie; 
    private final int ageRestriction; 

    // Default constructor for Jackson
    public MovieCategoryFlyweight() {
        this.type = "";
        this.descriptionOfMovie = "";
        this.ageRestriction = 0;
    }

    @JsonCreator
    public MovieCategoryFlyweight(
            @JsonProperty("type") String type,
            @JsonProperty("descriptionOfMovie") String descriptionOfMovie,
            @JsonProperty("ageRestriction") int ageRestriction) {
        this.type = type;
        this.descriptionOfMovie = descriptionOfMovie;
        this.ageRestriction = ageRestriction;
    }

    public String getType() {
        return type;
    }

    public String getDescriptionOfMovie() {
        return descriptionOfMovie;
    }

    public int getAgeRestriction() {
        return ageRestriction;
    }

    @Override
    public String toString() {
        return "MovieCategoryFlyweight{" +
                "type='" + type + '\'' +
                ", descriptionOfMovie='" + descriptionOfMovie + '\'' +
                ", ageRestriction=" + ageRestriction +
                '}';
    }
}