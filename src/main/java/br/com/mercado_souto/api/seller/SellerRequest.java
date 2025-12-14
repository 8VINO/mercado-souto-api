package br.com.mercado_souto.api.seller;

import org.hibernate.validator.constraints.br.CNPJ;

import br.com.mercado_souto.model.seller.Seller;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerRequest {

    @NotBlank(message = "O CNPJ é obrigatório")
    @CNPJ(message = "CNPJ inválido. Ex: 99.999.999/9999-99 ou 14 dígitos numéricos VÁLIDOS.")
    private String cnpj;

    public Seller build() {
        return Seller.builder()
                .cnpj(cnpj)
                .build();
    }
}
