package application.config;

import application.model.Event;
import application.model.Ticket;
import application.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;
@Configuration
@PropertySource({"classpath:event.properties"})
public class MapConfigurer {
    @Bean
    Map<Integer, Event> eventMap(@Value("#{${event}}") Map<Integer, Event> eventMap) {
        return eventMap;
    }
    @Bean
    Map<Integer, Ticket> ticketMap(@Value("#{${ticket}}") Map<Integer, Ticket> eventMap) {
        return eventMap;
    }

}
