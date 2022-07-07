package application.service;

import application.repository.EventRepo;
import application.model.Event;
import application.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

public class EventService {
    @Autowired
    private EventRepo eventRepo;
    @Autowired
    private TicketService ticketService;

    public Integer createEvent (String eventName, Integer numberOfTickets) {
        Event event = Event.builder().name(eventName).build();
        Collection<Ticket> tickets = ticketService.createTicket(numberOfTickets, event);
        event.setAllTickets(new HashSet<>(tickets));
        event.setAvaliableTickets(new HashSet<>(tickets));
        return eventRepo.saveEvent(event);
    }

    public Event createEventReturnEvent (String eventName, Integer numberOfTickets) {
        Integer eventId = createEvent(eventName, numberOfTickets);
        return eventRepo.findEventById(eventId);
    }

    public Event findEventById (Integer id) {
        return eventRepo.findEventById(id);
    }

    public Optional<Event> findEventByName (String name) {
        return Optional.ofNullable(eventRepo.findEventByName(name));
    }
}
