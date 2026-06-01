package rs.agroshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


@Entity
@Table(name  = "picture")
@Getter
@Setter
@NoArgsConstructor
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "picture_id")
    private Integer pictureId;

    @Column(name = "path", nullable = false, length = 255)
    private String path;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="machine_id", nullable = false)
    private Machine machine;

}
