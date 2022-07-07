package application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Ticket implements ModelEntity {
    private Integer id;
    private Integer placeNumber;
    @EqualsAndHashCode.Exclude
    private Event event;
}
