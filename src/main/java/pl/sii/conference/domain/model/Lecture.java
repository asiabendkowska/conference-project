package pl.sii.conference.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lecture {

    @NotNull
    @Min(1)
    @Max(12)
    private Integer id;

    @NotBlank
    private String title;

    @NotNull
    @Min(1)
    @Max(4)
    private Integer timeSlotId;

    @NotNull
    @Min(1)
    @Max(3)
    private Integer categoryId;


}
