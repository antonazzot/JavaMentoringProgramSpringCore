package application.service;

import application.AbstractTest;
import application.config.StorageType;
import application.model.Event;
import application.model.User;
import application.repository.EventRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class EventServiceTest extends AbstractTest {

    private String eventName = "TestEventName";

    private User user;
    private Event event;

    @Autowired
    private EventRepo eventRepo;

    @BeforeEach
    private void init () {
        database.cleanStorageByType(StorageType.EVENT);
    }

    @Test
    void createEvent() {
        var eventMap = database.getStorageMap().get(StorageType.EVENT);
        assertThat(eventMap.values()).isEmpty();
        event = Event.builder()
                .name(eventName)
                .build();
        Integer integer = eventRepo.saveEvent(event);
        assertThat(eventMap.values()).hasSize(1);

    }

    @Test
    void createEventReturnEvent() {
        var eventMap = database.getStorageMap().get(StorageType.EVENT);
        assertThat(eventMap.values()).isEmpty();
        event = Event.builder()
                .name(eventName)
                .build();
        Integer integer = eventRepo.saveEvent(event);
        assertThat(eventMap.values()).hasSize(1);
    }

    @Test
    void findEventById() {
        var eventMap = database.getStorageMap().get(StorageType.EVENT);
        assertThat(eventMap.values()).isEmpty();
        event = Event.builder()
                .name(eventName)
                .build();
        Integer integer = eventRepo.saveEvent(event);
        Event eventById = eventRepo.findEventById(integer);
        assertThat(eventById.getId()).isEqualTo(integer);
    }

    @Test
    void findEventByName() {
        var eventMap = database.getStorageMap().get(StorageType.EVENT);
        assertThat(eventMap.values()).isEmpty();
        event = Event.builder()
                .name(eventName)
                .build();
        Integer integer = eventRepo.saveEvent(event);
        Event eventByName = eventRepo.findEventByName(eventName);
        assertThat(eventByName.getName()).isEqualTo(eventName);
        assertThat(eventMap.values()).hasSize(1);
        assertThat(eventByName.getId()).isEqualTo(integer);
    }
}