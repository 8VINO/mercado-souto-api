package br.com.mercado_souto.api.seller;

import java.math.BigDecimal;

import br.com.mercado_souto.model.seller.Seller;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerRequest {

    private String cnpj;

    private Integer sales;

    private BigDecimal balance;

    public Seller build(){
        return Seller.builder()
                        .cnpj(cnpj)
                        .sales(sales)
                        .balance(balance)
                        .build();
    }
}
