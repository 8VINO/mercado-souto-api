package br.com.mercado_souto.api.client;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientUpdateRequest {
    @Size(min = 2, max = 70, message = "O nome deve ter ao menos 2 letras e no máximo 70 caracteres.")
    private String name; 

    @Email(message = "E-mail inválido!")
    private String email; 

    @Size(min = 11, max = 16, message = "Número de telefone inválido. Deve ter no mínimo 11 caracteres (ex: XXXXXXXXXXX)")
    private String phone; 

    
    @CPF(message = "CPF inválido. Ex: 123.123.123-12 ou 11 dígitos numéricos VÁLIDOS.") 
    private String cpf;
    
   
    @Size(min = 8, max = 50, message = "A senha deve ter no mínimo 8 e no máximo 50 caracteres.")
    private String password; 
}
