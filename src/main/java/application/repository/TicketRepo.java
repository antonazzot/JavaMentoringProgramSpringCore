package application.repository;

import application.config.StorageType;
import application.databaseamulation.Database;
import application.model.Event;
import application.model.ModelEntity;
import application.model.Ticket;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TicketRepo {
    private final Database database;

    public Integer saveTicket (Ticket ticket) {
        return database.addEntity(ticket);
    }

    public Ticket findTicketById (Integer id) {
        return (Ticket) database.getEntityById(StorageType.TICKET, id);
    }

    public Ticket deleteTicket (Integer id) {
        return (Ticket) database.deleteEntityById(StorageType.TICKET, id);
    }

    public Collection<Ticket> getAllTickets () {
        Collection<ModelEntity> values = database.findAll(StorageType.TICKET).values();
        return values.stream().map(Ticket.class::cast).toList();
    }

    public Ticket findTicketByName(Integer placeNumber) {
        return getAllTickets().stream().filter(ticket -> ticket.getPlaceNumber().equals(placeNumber)).findFirst().orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Set<Ticket> findTicketsByEvent(Event event) {
        return getAllTickets().stream().filter(ticket -> ticket.getEvent().getId().equals(event.getId())).collect(Collectors.toSet());
    }

    public Set<Ticket> saveAllTickets(Collection <Ticket> collection) {
        Set <Ticket> result = new HashSet<>();
        for (Ticket ticket : collection) {
            Integer saveId = saveTicket(ticket);
            result.add(findTicketById(saveId));
        }
    return result;
    }
}
