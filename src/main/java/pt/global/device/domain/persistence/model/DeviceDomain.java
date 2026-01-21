package pt.global.device.domain.persistence.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String brand;
    @Column(nullable = false)
    private StateEnum state;
    @Column(nullable = false)
    private LocalDateTime creationDateTime;

}
