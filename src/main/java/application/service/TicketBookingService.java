package application.service;

import application.model.Event;
import application.model.Order;
import application.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Slf4j
@Data
@NoArgsConstructor
public class TicketBookingService {
    private  OrderService orderService;
    private  EventService eventService;
    private  TicketService ticketService;
    private  UserService userService;

    public TicketBookingService(@Autowired OrderService orderService,
                                @Autowired EventService eventService,
                                @Autowired TicketService ticketService,
                                @Autowired UserService userService) {
        this.orderService = orderService;
        this.eventService = eventService;
        this.ticketService = ticketService;
        this.userService = userService;
    }

    public Order createOrder (String userName, String eventName, Integer amountOfPlace) {
        Optional<Event> eventByName = eventService.findEventByName(eventName);
        Event event;
        Integer id = 0;
        if (eventByName.isEmpty()) {
            log.info("Event with name not found ={}", eventName);
        }
        else {
            event = eventByName.get();
            if(event.getAvaliableTickets().size()<amountOfPlace) {
                log.info("It's not enough place for this event = {}", "Count of free places = " +event.getAvaliableTickets().size());
            }
            else {
                User user = userService.findByName(userName).orElse(userService.createUserReturnUser(userName));
                 id = orderService.createOrder(event, user, amountOfPlace);
            }
        }
        return orderService.findById(id);
    }

    public Order createOrderByPlaceNumber (String userName, String eventName, List<Integer> placeNumber) {
        Optional<Event> eventByName = eventService.findEventByName(eventName);
        Event event;
        Integer id = 0;
        if (eventByName.isEmpty()) {
            log.info("Event with name not found ={}", eventName);
        }
        else {
            event = eventByName.get();
            if(event.getAvaliableTickets().size()< placeNumber.size()) {
                log.info("It's not enough place for this event = {}", "Count of free places = " +event.getAvaliableTickets().size());
            }
            else {
                User user = userService.findByName(userName).orElse(userService.createUserReturnUser(userName));
                try {
                    id = orderService.createOrder(event, user, placeNumber);
                }
                catch (RuntimeException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return orderService.findById(id);
    }
}
