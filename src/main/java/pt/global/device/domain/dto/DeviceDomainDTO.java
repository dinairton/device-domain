package pt.global.device.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import pt.global.device.domain.persistence.model.StateEnum;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDomainDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String brand;
    @NotNull
    private StateEnum state;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime creationDateTime;

}
