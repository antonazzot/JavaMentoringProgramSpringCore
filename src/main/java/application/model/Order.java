package application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order implements ModelEntity {
    private Integer id;
    private User user;
    private Event event;
    private Set<Ticket> tickets;
}
