package br.com.mercado_souto.api.product;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductUpdateRequest {
    @Size(min = 5, max = 100, message = "O título deve ter entre 5 e 100 caracteres")
    private String title;

    @Size(min = 10, max = 10000, message = "A especificação deve ter no mínimo 10 e no máximo 10000 caracteres")
    private String specification; 

    @Size(min = 20, max = 3000, message = "A descrição deve ter entre 20 e 3000 caracteres")
    private String description;

    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero")
    private BigDecimal price;
 
    @Min(value = 1, message = "O estoque mínimo deve ser 1")
    private Integer stock;

    @Min(value = 1, message = "O ID da categoria deve ser um valor positivo")
    private Long idCategory;

    private List<String> imageURL;
}
