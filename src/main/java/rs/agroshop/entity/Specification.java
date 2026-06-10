package rs.agroshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name  = "specification")
@Getter
@Setter
@NoArgsConstructor
public class Specification {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "specification_id")
    private Integer specificationId;

    @Column(name="name", nullable = false, length = 50)
    private String name;

    @Column(name = "value", nullable = false, length = 50)
    private String value;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="machine_id", nullable = false)
    private Machine machine;
}
