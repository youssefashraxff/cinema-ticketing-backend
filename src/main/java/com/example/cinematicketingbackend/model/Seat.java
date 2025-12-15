package com.example.cinematicketingbackend.model;

public class Seat {
    private int seatNumber;
    private String status="available";//booked,locked,available
    private int hallid;
    public int getSeatNumber() {
        return seatNumber;
    }
    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public int getHallid() {
        return hallid;
    }
    public void setHallid(int hallid) {
        this.hallid = hallid;
    }

}
