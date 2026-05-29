package rs.agroshop.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name  = "cart_item")
@Getter
@Setter
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Integer cartItemId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;

    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subTotal;

}
