package com.example.cinematicketingbackend.patterns.decorator;

public class PopcornDecorator extends TicketDecorator {
    public PopcornDecorator(Ticket ticket) { super(ticket); }

    @Override
    public String getDescription() { return ticket.getDescription() + " + Popcorn"; }

    @Override
    public double getCost() { return ticket.getCost() + 80.0; } 
}