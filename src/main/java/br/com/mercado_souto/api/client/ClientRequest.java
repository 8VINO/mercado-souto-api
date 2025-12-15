package br.com.mercado_souto.api.client;

import org.hibernate.validator.constraints.br.CPF;

import br.com.mercado_souto.model.acess.User;
import br.com.mercado_souto.model.client.Client;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequest {

    @NotBlank(message = "Campo obrigatório.")
    @Size(min = 2, max = 70, message = "O nome deve ter ao menos 2 letras e no máximo 70 caracteres.")
    private String name;

    @NotBlank(message = "Campo obrigatório.")
    @Email(message = "E-mail inválido!")
    private String email;

    @NotBlank(message = "Campo obrigatório.")
    @Size(min = 11, max = 16, message = "Número de telefone inválido. Deve ter no mínimo 11 caracteres (ex: XXXXXXXXXXX)")
    private String phone;

    @NotBlank(message = "O CPF é obrigatório")
    @CPF(message = "CPF inválido. Ex: 123.123.123-12 ou 11 dígitos numéricos VÁLIDOS.") 
    private String cpf;

    @NotBlank(message = "Campo obrigatório.")
    @Size(min = 8, max = 50, message = "A senha deve ter no mínimo 8 e no máximo 50 caracteres.")
    private String password;

    public User buildUser() {
        return User.builder()
                .username(email)
                .password(password)
                .build();
    }

    public Client build() {
        return Client.builder()
                .user(buildUser())
                .name(name)
                .email(email)
                .cpf(cpf)
                .phone(phone)
                .build();
    }
}
