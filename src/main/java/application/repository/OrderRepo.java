package application.repository;

import application.config.StorageType;
import application.databaseamulation.Database;
import application.model.Order;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderRepo {
    @Autowired
    private Database database;

    public Integer saveOrder (Order order) {
        return database.addEntity(order);
    }
    public Order saveOrderReturnOerder (Order order) {
        return findOrderById(saveOrder(order));
    }

    public Order findOrderById (Integer id) {
        return (Order) database.getEntityById(StorageType.ORDER, id);
    }

    public Order deleteTicket (Integer id) {
        return (Order) database.deleteEntityById(StorageType.ORDER, id);
    }


}
