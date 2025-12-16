package com.example.cinematicketingbackend.decorator;

public class ThreeDGlassesDecorator extends TicketDecorator {

    public ThreeDGlassesDecorator(Ticket ticket) {
        super(ticket);
    }

    @Override
    public String getDescription() {
        return ticket.getDescription() + " + 3D Glasses";
    }

    @Override
    public double getCost() {
        return ticket.getCost() + 50.0; 
    }
}