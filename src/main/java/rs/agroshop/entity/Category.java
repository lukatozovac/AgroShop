package rs.agroshop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import rs.agroshop.model.CategoryType;
import lombok.NoArgsConstructor;

@Entity
@Table(name  = "category")
@Getter
@Setter
@NoArgsConstructor
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name="category_name", nullable = false, length = 100)
    private String categoryName;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 255)
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(name ="category_type", nullable = false)
    private CategoryType categoryType;
}
