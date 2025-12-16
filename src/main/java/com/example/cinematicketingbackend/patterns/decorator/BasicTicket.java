package com.example.cinematicketingbackend.patterns.decorator;

public class BasicTicket implements Ticket {
    private double seatPrice;
    private String seatId;

    public BasicTicket(String seatId, double seatPrice) {
        this.seatId = seatId;
        this.seatPrice = seatPrice;
    }

    @Override
    public String getDescription() {
        return "Seat " + seatId;
    }

    @Override
    public double getCost() {
        return seatPrice;
    }
}