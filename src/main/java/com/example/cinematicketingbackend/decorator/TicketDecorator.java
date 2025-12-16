package com.example.cinematicketingbackend.decorator;

public abstract class TicketDecorator implements Ticket {
    protected Ticket ticket;

    public TicketDecorator(Ticket ticket) {
        this.ticket = ticket;
    }

    public String getDescription() { return ticket.getDescription(); }
    public double getCost() { return ticket.getCost(); }
}