package br.com.mercado_souto.api.client;


import br.com.mercado_souto.model.acess.User;
import br.com.mercado_souto.model.client.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequest {

    private String name;

    private String email;

    private String password;

    private String cpf;

    private String phone;

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
                .password(password)
                .cpf(cpf)
                .phone(phone)
                .build();
    }
}
