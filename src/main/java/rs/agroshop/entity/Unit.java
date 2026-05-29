package rs.agroshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name  = "unit")
@Getter
@Setter
@NoArgsConstructor
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unit_id")
    private Integer unitId;

    @Column(name="serial_number", unique = true, length = 6, nullable = false)
    private String serialNumber;

    @Column(name="availability")
    private Boolean availability;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="machine_id", nullable = false)
    private Machine machine;
}
