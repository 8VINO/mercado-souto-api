package br.com.mercado_souto.api.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {
    @NotNull(message = "O campo 'rating' é obrigatório.")
    @Min(value = 1, message = "A avaliação mínima é de 1 estrela.")
    @Max(value = 5, message = "A avaliação máxima é de 5 estrelas.")
    private Integer rating;
    
    @Size(max = 1000, message = "O comentário deve ter no máximo 1000 caracteres.")
    private String comment;
}
