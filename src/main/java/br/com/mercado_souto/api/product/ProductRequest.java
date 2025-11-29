package br.com.mercado_souto.api.product;

import java.math.BigDecimal;

import br.com.mercado_souto.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
   
    private String title;

    
    private String description;

   
    private BigDecimal price;

   
    private Integer stock;

    
    private Long idCategory;

   


    public Product build(){
        return Product.builder()
                        .title(title)
                        .description(description)
                        .price(price)
                        .stock(stock)
                        .build();
    }
}
