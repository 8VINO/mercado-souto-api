package br.com.mercado_souto.api.acess;

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

public class AuthenticationRequest {

    @NotBlank(message = "Campo obrigatório.")
    @Email(message = "E-mail inválido!")
    private String email;

    @NotBlank(message = "Campo obrigatório.")
    @Size(min = 8, max = 50, message = "A senha deve ter no mínimo 8 e no máximo 50 caracteres.")
    private String password;

}
