package application.service;

import application.AbstractTest;
import application.config.StorageType;
import application.model.Event;
import application.model.Ticket;
import application.repository.TicketRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class TicketServiceTest extends AbstractTest {
    private Integer placeNumber = 5;
    private Ticket ticket;

    @Autowired
    private TicketRepo ticketRepo;

    @BeforeEach
    private void init () {
        ticket = Ticket.builder().placeNumber(placeNumber).build();
        database.cleanStorageByType(StorageType.TICKET);
    }

    @Test
    void createTicket() {
        var ticketMap = database.getStorageMap().get(StorageType.TICKET);
        assertThat(ticketMap.values()).isEmpty();
        Integer ticketId = ticketRepo.saveTicket(this.ticket);
        assertThat(ticketMap.values()).hasSize(1);
        Ticket ticketById = ticketRepo.findTicketById(ticketId);
        assertThat(ticketById.getId()).isEqualTo(ticketId);
        assertThat(ticketById.getPlaceNumber()).isEqualTo(placeNumber);
    }

    @Test
    void testCreateTicket() {
        var ticketMap = database.getStorageMap().get(StorageType.TICKET);
        assertThat(ticketMap.values()).isEmpty();
        Integer ticketId = ticketRepo.saveTicket(this.ticket);
        assertThat(ticketMap.values()).hasSize(1);
        Ticket ticketById = ticketRepo.findTicketById(ticketId);
        assertThat(ticketById.getId()).isEqualTo(ticketId);
        assertThat(ticketById.getPlaceNumber()).isEqualTo(placeNumber);
    }

    @Test
    void deleteTicketByplaceNumber() {
        var ticketMap = database.getStorageMap().get(StorageType.TICKET);
        assertThat(ticketMap.values()).isEmpty();
        Integer ticketId = ticketRepo.saveTicket(this.ticket);
        assertThat(ticketMap.values()).hasSize(1);
        Ticket deleteTicket = ticketRepo.deleteTicket(ticketId);
        assertThat(ticketMap.values()).isEmpty();
        assertThat(deleteTicket.getId()).isEqualTo(ticketId);
    }

    @Test
    void findByPlaceNumber() {
        var ticketMap = database.getStorageMap().get(StorageType.TICKET);
        assertThat(ticketMap.values()).isEmpty();
        Integer ticketId = ticketRepo.saveTicket(this.ticket);
        assertThat(ticketMap.values()).hasSize(1);
        Ticket ticketByPlace = ticketRepo.findTicketByName(placeNumber);
        assertThat(ticketByPlace.getId()).isEqualTo(ticketId);
    }

    @Test
    void findAllByEvent() {
        var ticketMap = database.getStorageMap().get(StorageType.TICKET);
        assertThat(ticketMap.values()).isEmpty();
        Integer eventId = eventService.createEvent("Event", 30);
        Event eventById = eventService.findEventById(eventId);
        assertThat(ticketMap.values()).hasSize(30);
        Set<Ticket> ticketsByEvent = ticketRepo.findTicketsByEvent(eventById);
        assertThat(ticketsByEvent).hasSize(30);
    }
}
