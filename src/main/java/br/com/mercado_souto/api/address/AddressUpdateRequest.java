package br.com.mercado_souto.api.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressUpdateRequest {
    
    @Pattern(regexp = "^\\d{5}-?\\d{3}$", message = "CEP inválido. Deve conter 8 dígitos numéricos, com ou sem hífen")
    private String cep;

    @Size(min = 3, max = 150, message = "A rua deve ter entre 3 e 150 caracteres")
    private String street;

    @Size(min = 1, max = 10, message = "O número deve ter entre 1 e 10 caracteres")
    private String number;

    @Size(max = 50, message = "O complemento deve ter no máximo 50 caracteres")
    private String complement;

    @Size(max = 100, message = "A informação adicional deve ter no máximo 100 caracteres")
    private String additionalInfo;

    private Boolean home;

    @Size(min = 5, max = 70, message = "O nome deve ter ao menos 5 letras e no máximo 70 caracteres.")
    private String contactName;

    @Size(min = 11, max = 16, message = "Número de telefone inválido. Deve ter no mínimo 11 caracteres (ex: XXXXXXXXXXX)")
    private String contactPhone;
}
