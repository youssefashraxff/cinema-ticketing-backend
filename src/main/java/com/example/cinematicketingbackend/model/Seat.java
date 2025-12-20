package com.example.cinematicketingbackend.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)

public class Seat {
    private char row;      // 'A' to 'E'
    private int number;    // 1 to 8

    public Seat() {}

    public Seat(char row, int number) {
        this.row = row;
        this.number = number;
    }

    public char getRow() { return row; }
    public void setRow(char row) { this.row = row; }

    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }

    public String getSeatId() {
        return "" + row + number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return row == seat.row && number == seat.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, number);
    }
    @Override
    public String toString() {
    return row + String.valueOf(number);
}
}