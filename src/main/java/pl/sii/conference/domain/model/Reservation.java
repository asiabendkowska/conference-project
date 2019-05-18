package pl.sii.conference.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Min(1)
    @Max(12)
    private Integer lectureId;

    @NotBlank
    private String lectureTitle;

    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    @Min(1)
    @Max(4)
    private Integer timeSlotId;

}
