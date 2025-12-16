package com.example.cinematicketingbackend.model;


public class MovieCategoryFlyweight {
    private final String type; 
    private final int ageRestriction; 

    public MovieCategoryFlyweight(String type,int ageRestriction) {
        this.type = type;
        this.ageRestriction = ageRestriction;
    }

    public String getType() {
        return type;
    }

    public int getAgeRestriction() {
        return ageRestriction;
    }

    @Override
    public String toString() {
        return "MovieCategoryFlyweight{" +
                "type='" + type + '\'' +
                ", ageRestriction=" + ageRestriction +
                '}';
    }

}

