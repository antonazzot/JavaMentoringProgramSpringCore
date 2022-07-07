package application.databaseamulation;

import application.config.IdFactory;
import application.config.StorageType;
import application.model.Event;
import application.model.ModelEntity;
import application.model.Order;
import application.model.Ticket;
import application.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Data
@AllArgsConstructor
@NoArgsConstructor
@PropertySource({"classpath:event.properties"})
public class Database {

    private Map<Integer, User> userMap;
    private Map<Integer, Event> eventMap;
    private Map<Integer, Ticket> ticketMap;
    private Map <Integer, Order> orderMap;

    private Map <StorageType, Map<Integer, ?>>storageMap;
    private IdFactory idFactory;

    public Database(@Value("${userMapProp}") Map<Integer, User> userMap,
                    @Autowired Map<Integer, Event> eventMap,
                    @Autowired Map<Integer, Ticket> ticketMap,
                    @Autowired IdFactory idFactory) {
        this.userMap = userMap;
        this.eventMap = eventMap;
        this.ticketMap = ticketMap;
        this.idFactory=idFactory;
        this.orderMap=new ConcurrentHashMap<>();
    }


    private void initMethod() {
        storageMap = new ConcurrentHashMap<>();
        orderMap=new ConcurrentHashMap<>();
        storageMap.put(StorageType.USER, userMap);
        storageMap.put(StorageType.EVENT, eventMap);
        storageMap.put(StorageType.TICKET, ticketMap);
        storageMap.put(StorageType.ORDER, orderMap);
    }

    public Integer addEntity(Object object) {
        Integer idAddedEntity;
        if (object instanceof User user) {
            Map<Integer, User> castMap = (Map<Integer, User>) storageMap.get(StorageType.USER);
            idAddedEntity = idFactory.idBuilder(castMap.values().size());
            user.setId(idAddedEntity);
            castMap.put(idAddedEntity, user);
            storageMap.replace(StorageType.USER, castMap);
        } else if (object instanceof Event event) {
            Map<Integer, Event> castMap = (Map<Integer, Event>) storageMap.get(StorageType.EVENT);
            idAddedEntity =idFactory.idBuilder(castMap.values().size());
            event.setId(idAddedEntity);
            castMap.put(idAddedEntity, event);
            storageMap.replace(StorageType.EVENT, castMap);
        }
        else if (object instanceof Ticket ticket) {
            Map<Integer, Ticket> castMap = (Map<Integer, Ticket>) storageMap.get(StorageType.TICKET);
            idAddedEntity =  idFactory.idBuilder(castMap.values().size());
            ticket.setId(idAddedEntity);
            castMap.put(idAddedEntity, ticket);
            storageMap.replace(StorageType.TICKET, castMap);
        }
        else  if (object instanceof Order order){
            Map<Integer, Order> castMap = (Map<Integer, Order>) storageMap.get(StorageType.ORDER);
            idAddedEntity =  idFactory.idBuilder(castMap.values().size());
            order.setId(idAddedEntity);
            castMap.put(idAddedEntity, order);
            storageMap.replace(StorageType.TICKET, castMap);
        }
        else throw new RuntimeException("Entity Out of valid value");

        return idAddedEntity;
    }

    public ModelEntity getEntityById (StorageType type, Integer id) {
        switch (type){
            case USER -> {return  (User)storageMap.get(type).get(id);}
            case EVENT -> {return  (Event)storageMap.get(type).get(id);}
            case TICKET -> {return  (Ticket)storageMap.get(type).get(id);}
            case ORDER -> {return (Order)storageMap.get(type).get(id);}
            default -> throw new RuntimeException("Entity Out of valid value");
        }
    }

    public ModelEntity deleteEntityById (StorageType type, Integer id) {
        switch (type){
            case USER -> {return  (User)storageMap.get(type).remove(id);}
            case EVENT -> {return  (Event)storageMap.get(type).remove(id);}
            case TICKET -> {return  (Ticket)storageMap.get(type).remove(id);}
            case ORDER -> {return (Order)storageMap.get(type).remove(id);}
            default -> throw new RuntimeException("Entity Out of valid value");
        }
    }

    public Map<Integer, ModelEntity> findAll (StorageType type) {
        return (Map<Integer, ModelEntity>) storageMap.get(type);
    }

    public void  cleanStorageByType (StorageType storageType) {
        storageMap.get(storageType).clear();
    }

}