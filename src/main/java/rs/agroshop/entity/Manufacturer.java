package rs.agroshop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name  = "manufacturer")
@Getter
@Setter
@NoArgsConstructor
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manufacturer_id")
    private Integer manufacturerId;

    @Column(name="name", nullable = false, length = 50)
    private String name;

    @Column(name="made_in", nullable = false, length = 50)
    private String madeIn;

    @Column(nullable = false, length = 255)
    private String logo;

}
    