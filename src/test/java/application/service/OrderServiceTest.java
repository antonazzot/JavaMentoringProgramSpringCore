package application.service;

import application.AbstractTest;
import application.config.StorageType;
import application.model.Event;
import application.model.Order;
import application.model.Ticket;
import application.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class OrderServiceTest extends AbstractTest {
    private String userName = "TestUserName";
    private String eventName = "TestEventName";
    private Integer eventPlaces = 20;

    private User user;
    private Event event;
    private Ticket ticket;
    private Set<Ticket> ticketSet;


    @Autowired
    private OrderService orderRepo;

    @BeforeEach
    private void init () {
        user = userService.createUserReturnUser(userName);
        event = eventService.createEventReturnEvent(eventName, eventPlaces);
        ticketSet = event.getAllTickets();
        database.cleanStorageByType(StorageType.ORDER);
    }

    @Test
    void createOrder() {
       var orderMap = database.getStorageMap().get(StorageType.ORDER);
       assertThat(orderMap).isEmpty();
        Integer order1 = orderRepo.createOrder(event, user, 2);
        Order byId = orderService.findById(order1);
        assertThat(orderMap).hasSize(1);
        assertThat(byId.getEvent()).isEqualTo(event);
        assertThat(order1).isEqualTo(byId.getId());
        assertThat(byId.getUser()).isEqualTo(user);
        assertThat(byId.getTickets()).hasSize(2);
    }

    @Test
    void findById() {
        Integer orderId = orderService.createOrder(event, user, 4);
        Order byId = orderRepo.findById(orderId);
        assertThat(byId.getId()).isEqualTo(orderId);
        assertThat(byId.getEvent()).isEqualTo(event);
    }

    @Test
    void testCreateOrder() {
        var orderMap = database.getStorageMap().get(StorageType.ORDER);
        assertThat(orderMap).isEmpty();
        Integer order1 = orderRepo.createOrder(event, user, 2);
        Order byId = orderService.findById(order1);
        assertThat(orderMap).hasSize(1);
        assertThat(byId.getEvent()).isEqualTo(event);
        assertThat(order1).isEqualTo(byId.getId());
        assertThat(byId.getUser()).isEqualTo(user);
        assertThat(byId.getTickets()).hasSize(2);
    }
}