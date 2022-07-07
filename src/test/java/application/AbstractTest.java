package application;

import application.databaseamulation.Database;
import application.service.EventService;
import application.service.OrderService;
import application.service.TicketService;
import application.service.UserService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/ApplicationContext.xml")
public abstract class AbstractTest {
    @Autowired
    protected OrderService orderService;
    @Autowired
    protected EventService eventService;
    @Autowired
    protected TicketService ticketService;
    @Autowired
    protected UserService userService;
    @Autowired
    protected Database database;
}
