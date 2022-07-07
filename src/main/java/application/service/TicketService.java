package application.service;

import application.repository.TicketRepo;
import application.model.Event;
import application.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class TicketService {
    @Autowired
    private TicketRepo ticketRepo;

    public Integer createTicket (Integer placeNumber) {
        return ticketRepo.saveTicket(Ticket.builder().placeNumber(placeNumber).build());
    }

    public Collection<Ticket> createTicket (Integer amountOfPlace, Event event) {
        Set<Ticket> tickets = new HashSet<>();
        for (int i = amountOfPlace; i > 0; i--) {
            tickets.add(Ticket.builder()
                            .placeNumber(amountOfPlace)
                            .event(event)
                    .build());
            amountOfPlace--;
        }
        return ticketRepo.saveAllTickets(tickets);
    }

    public Ticket deleteTicketByplaceNumber (Integer placeNumber) {
        return ticketRepo.deleteTicket(ticketRepo.findTicketByName(placeNumber).getId());
    }

    public Ticket findByPlaceNumber (Integer placeNumber) {
        return ticketRepo.findTicketByName(placeNumber);
    }

    public Collection<Ticket> findAllByEvent (Event event) {
     return ticketRepo.findTicketsByEvent(event);
    }
}
