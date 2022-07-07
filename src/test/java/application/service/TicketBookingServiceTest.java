package application.service;

import application.AbstractTest;
import application.config.StorageType;
import application.model.Event;
import application.model.Order;
import application.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TicketBookingServiceTest extends AbstractTest {

    private String userName = "TestUserName";
    private String eventName = "TestEventName";
    private Integer eventPlaces = 20;
    private Integer amountOfPlacesForBocking = 4;
    private List<Integer> listWithNumberOfPlaces = List.of(4,5,6,7);

    private User user;

    @BeforeEach
    private void init () {
        user = userService.createUserReturnUser(userName);
        database.cleanStorageByType(StorageType.ORDER);
        database.cleanStorageByType(StorageType.USER);
        database.cleanStorageByType(StorageType.EVENT);
        database.cleanStorageByType(StorageType.TICKET);
    }

    @Test
    void createOrder() {
        Event nullEvent = eventService.findEventByName(eventName).orElse(null);
        assertThat(nullEvent).isNull();
        Integer eventId = eventService.createEvent(eventName, eventPlaces);
        Event event = eventService.findEventById(eventId);
        assertThat(event.getId()).isEqualTo(eventId);
        assertThat(event.getAvaliableTickets().size()).isEqualTo(eventPlaces).isEqualTo(event.getAllTickets().size());
        assertThat(event.getName()).isEqualTo(eventName);

        User user1 = userService.createUserReturnUser(userName);
        Integer orderid1 = orderService.createOrder(event, user1, listWithNumberOfPlaces);

        assertThat(event.getAvaliableTickets()).hasSize(eventPlaces-listWithNumberOfPlaces.size());
        assertThat(user1.getOrder().getTickets()).hasSameSizeAs(listWithNumberOfPlaces);
        assertThat(event.getAvaliableTickets()).doesNotContainAnyElementsOf(user1.getOrder().getTickets());


        Integer orderId = orderService.createOrder(event, user, amountOfPlacesForBocking);
        Order order = orderService.findById(orderId);

        assertThat(order.getId()).isEqualTo(orderId);
        assertThat(order.getEvent()).isEqualTo(event);
        assertThat(order.getUser()).isEqualTo(user);

        assertThat(event.getAvaliableTickets().size()).isEqualTo(eventPlaces-amountOfPlacesForBocking-listWithNumberOfPlaces.size()).isEqualTo(event.getAllTickets().size()-amountOfPlacesForBocking-listWithNumberOfPlaces.size());
        assertThat(user.getOrder().getTickets()).hasSize(amountOfPlacesForBocking);
        assertThat(event.getAvaliableTickets()).doesNotContainAnyElementsOf(user.getOrder().getTickets());

    }

    @Test
    void createOrderByPlaceNumber() {
    }
}