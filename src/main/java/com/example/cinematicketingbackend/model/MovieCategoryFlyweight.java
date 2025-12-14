package com.example.cinematicketingbackend.model;


public class MovieCategoryFlyweight {
    private final String type; 
    private final String descriptionOfMovie; 
    private final int ageRestriction; 

    public MovieCategoryFlyweight(String type, String descriptionOfMovie, int ageRestriction) {
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

    // @Override
    // public boolean equals(Object o) {
    //     if (this == o) return true;
    //     if (o == null || getClass() != o.getClass()) return false;
    //     MovieCategoryFlyweight that = (MovieCategoryFlyweight) o;
    //     return ageRestriction == that.ageRestriction &&
    //             type.equals(that.type) &&
    //             (descriptionOfMovie != null ? descriptionOfMovie.equals(that.descriptionOfMovie) : that.descriptionOfMovie == null);
    // }

    // @Override
    // public int hashCode() {
    //     int result = type.hashCode();
    //     result = 31 * result + (descriptionOfMovie != null ? descriptionOfMovie.hashCode() : 0);
    //     result = 31 * result + ageRestriction;
    //     return result;
    // }
}

