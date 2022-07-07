package application.repository;

import application.config.StorageType;
import application.databaseamulation.Database;
import application.model.Event;
import application.model.ModelEntity;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
@RequiredArgsConstructor
public class EventRepo {
    private final Database database;

    public Integer saveEvent (Event event) {
        return database.addEntity(event);
    }

    public Event findEventById (Integer id) {
        Event entityById = (Event) database.getEntityById(StorageType.EVENT, id);
        return entityById;
    }

    public Event deleteEvent (Integer id) {
        return (Event) database.deleteEntityById(StorageType.EVENT, id);
    }

    public Collection<Event> getAllEvents () {
        Collection<ModelEntity> values = database.findAll(StorageType.EVENT).values();
        return values.stream().map(Event.class::cast).toList();
    }

    public Event findEventByName(String name) {
        return getAllEvents().stream().filter(event -> event.getName().equals(name)).findFirst().orElse(null);
    }
}
