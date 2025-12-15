package br.com.mercado_souto.api.seller;

import org.hibernate.validator.constraints.br.CNPJ;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SellerUpdateRequest {
    @CNPJ(message = "CNPJ inválido. Ex: 99.999.999/9999-99 ou 14 dígitos numéricos VÁLIDOS.")
    private String cnpj;
}
