package com.example.cinematicketingbackend.model;

public class Seat {
    private String seatId; // e.g., "A1", "B5"
    private char row;      // 'A' through 'E'
    private int number;    // 1 through 8
    private String status = "available"; // available, locked, booked
    private int hallId;

    public Seat() {}

    public Seat(char row, int number, int hallId) {
        this.row = row;
        this.number = number;
        this.hallId = hallId;
        this.seatId = "" + row + number;
    }

    
    public String getSeatId() { return seatId; }
    public void setSeatId(String seatId) { this.seatId = seatId; }
    
    public char getRow() { return row; }
    public void setRow(char row) { this.row = row; }
    
    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public int getHallId() { return hallId; }
    public void setHallId(int hallId) { this.hallId = hallId; }
}