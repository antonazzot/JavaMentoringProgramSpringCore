package application;

import application.service.TicketBookingService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationMain {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        TicketBookingService ticketBookingService = (TicketBookingService) ac.getBean("TicketBookingService");
        ticketBookingService.createOrder("Anton", "Event", 20);
    }
}

