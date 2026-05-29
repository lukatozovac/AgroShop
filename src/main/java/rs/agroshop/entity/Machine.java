package rs.agroshop.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name  = "machine")
@Getter
@Setter
@NoArgsConstructor
public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "machine_id")
    private Integer machineId;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "release_year", nullable = false)
    private  Integer releaseYear;

    @Column(name = "icon_path", nullable = false, length = 255)
    private String iconPath;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "manufacturer_id", nullable = false)
    private Manufacturer manufacturer;

}
