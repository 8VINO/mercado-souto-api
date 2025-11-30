package br.com.mercado_souto.api.address;

import br.com.mercado_souto.model.address.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {

    private String cep;

    private String street;

    private String number;

    private String complement;

    private String additionalInfo;

    private Boolean home;

    private String contactName;

    private String contactPhone;

    public Address build() {
        return Address.builder()
                .cep(cep)
                .street(street)
                .number(number)
                .complement(complement)
                .additionalInfo(additionalInfo)
                .home(home)
                .contactName(contactName)
                .contactPhone(contactPhone)
                .build();
    }
}
