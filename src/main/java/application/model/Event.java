package application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Event implements ModelEntity {
    private Integer id;
    private String name;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Ticket> allTickets = new HashSet<>();
    @ToString.Exclude
    private Set<Ticket> avaliableTickets = new HashSet<>();
}
