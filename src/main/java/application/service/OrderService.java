package application.service;

import application.repository.OrderRepo;
import application.model.Event;
import application.model.Order;
import application.model.Ticket;
import application.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrderService {
    @Autowired
    private  OrderRepo orderRepo;

    public Integer createOrder (Event event, User currentUser, int amountOfPlace) {
        Set<Ticket> orderTickets = new HashSet<>();
        for (int i = 0; i != amountOfPlace; i++) {
            Ticket ticket = event.getAvaliableTickets().stream().toList().get(0);
            event.getAvaliableTickets().remove(ticket);
            orderTickets.add(ticket);
        }
        Order build = Order.builder()
                .event(event)
                .tickets(orderTickets)
                .user(currentUser)
                .build();

        Order order = orderRepo.saveOrderReturnOerder(build);
        currentUser.setOrder(order);
        return order.getId();
    }

    public Order findById (Integer id) {
        return orderRepo.findOrderById(id);
    }

    public Integer createOrder (Event event, User currentUser, List<Integer> placeNumber) {
        Set<Ticket> orderTickets = new HashSet<>();
        for (int i = 0; i != placeNumber.size(); i++) {
            int finalI = placeNumber.get(i);
            Ticket ticket = event.getAvaliableTickets().stream().toList()
                    .stream()
                    .filter(t->t.getPlaceNumber()==finalI).findFirst().orElseThrow(()->new RuntimeException("This ticket is already bocking "));
            event.getAvaliableTickets().remove(ticket);
            orderTickets.add(ticket);
        }
        Order build = Order.builder()
                .event(event)
                .tickets(orderTickets)
                .user(currentUser)
                .build();
        Order order = orderRepo.saveOrderReturnOerder(build);
        currentUser.setOrder(order);
        return order.getId();
    }
}
