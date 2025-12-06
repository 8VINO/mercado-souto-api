package br.com.mercado_souto.api.acess;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mercado_souto.model.acess.Role;
import br.com.mercado_souto.model.acess.User;
import br.com.mercado_souto.model.acess.UserService;
import br.com.mercado_souto.model.client.ClientService;
import br.com.mercado_souto.model.security.JwtService;

@RestController
@RequestMapping("/api/login")
@CrossOrigin
public class AuthenticationController {
    @Autowired
    private ClientService clientService;
    private final JwtService jwtService;

    private UserService userService;

    public AuthenticationController(JwtService jwtService, UserService userService) {

        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping
    public Map<Object, Object> login(@RequestBody AuthenticationRequest data) {

        User authenticatedUser = userService.authenticate(data.getEmail(), data.getPassword());
        /*Mesmo que o Client e o User geralmente tenham Id iguais por ser sequencial, eu busco o Id do  Client autenticado diretamente pelo User autenticado para garantir que estou pegando o Id certo.
        */
        Long clientId = clientService.findByUser(authenticatedUser).getId();
        String jwtToken = jwtService.generateToken(authenticatedUser);

        Map<Object, Object> loginResponse = new HashMap<>();
        loginResponse.put("clientId", clientId);
        loginResponse.put("token", jwtToken);
        loginResponse.put("tokenExpiresIn", jwtService.getExpirationTime());
        loginResponse.put("roles", authenticatedUser.getRoles()
                .stream()
                .map(Role::getName)
                .toList());

        return loginResponse;
    }

}
