package rs.agroshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "machine")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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
    private Integer releaseYear;

    @Column(name = "icon_path", nullable = false, length = 255)
    private String iconPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @OneToMany(mappedBy = "machine", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("machine") // Sprečava beskonačnu petlju
    private Set<Specification> specifications = new HashSet<>();

    @OneToMany(mappedBy = "machine", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("machine") // Sprečava beskonačnu petlju
    private Set<Picture> pictures = new HashSet<>();
}
