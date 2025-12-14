package br.com.mercado_souto.api.product;

import java.math.BigDecimal;

import br.com.mercado_souto.model.product.Product;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

   @NotBlank(message = "O título é obrigatório")
    @Size(min = 5, max = 100, message = "O título deve ter entre 5 e 100 caracteres")
    private String title;

    @Size(min = 10, max = 10000, message = "A especificação deve ter no mínimo 10 e no máximo 10000 caracteres")
    private String specification; 

    @NotBlank(message = "A descrição é obrigatória")
    @Size(min = 20, max = 3000, message = "A descrição deve ter entre 20 e 3000 caracteres")
    private String description;

    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero")
    private BigDecimal price;
  
    @NotNull(message = "O estoque é obrigatório")
    @Min(value = 1, message = "O estoque mínimo deve ser 1")
    private Integer stock;

    @NotNull(message = "O ID da categoria é obrigatório")
    @Min(value = 1, message = "O ID da categoria deve ser um valor positivo")
    private Long idCategory;

    @Size(max = 500, message = "O URL da imagem não pode exceder 500 caracteres")
    @Pattern(regexp = "^(http|https)://.*$", message = "O URL da imagem deve ser um link válido")
    private String imageURL;

    public Product build() {
        return Product.builder()
                .title(title)
                .specification(specification)
                .description(description)
                .price(price)
                .stock(stock)
                .build();
    }
}
