package br.com.mercado_souto.model.cart;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.mercado_souto.model.product.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name = "cart-item")

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItem  {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private Cart cart;

    private Boolean isSelected;
    @ManyToOne
    private Product product;

    private Integer quantity;

   public BigDecimal getSubtotal() {
        if (this.product == null || this.product.getPrice() == null || this.quantity == null) {
            return BigDecimal.ZERO;
        }
        return this.product.getPrice().multiply(BigDecimal.valueOf(this.quantity));
    }
}
