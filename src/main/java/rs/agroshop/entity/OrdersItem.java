package rs.agroshop.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name  = "orders_item")
@Getter
@Setter
@NoArgsConstructor
public class OrdersItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_item_id")
    private Integer ordersItemId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="orders_id", nullable = false)
    private Orders orders;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="unit_id", nullable = false)
    private Unit unit;
}
