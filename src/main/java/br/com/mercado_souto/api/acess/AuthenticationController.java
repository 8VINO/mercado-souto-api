package br.com.mercado_souto.api.acess;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mercado_souto.model.acess.User;
import br.com.mercado_souto.model.acess.UserService;
import br.com.mercado_souto.model.security.JwtService;

@RestController
@RequestMapping("/api/login")
@CrossOrigin
public class AuthenticationController {
    
    private final JwtService jwtService;

    private UserService userService;

    public AuthenticationController(JwtService jwtService, UserService userService) {

        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping
    public Map<Object, Object> login(@RequestBody AuthenticationRequest data) {

        User authenticatedUser = userService.authenticate(data.getEmail(), data.getPassword());

        String jwtToken = jwtService.generateToken(authenticatedUser);

        Map<Object, Object> loginResponse = new HashMap<>();
        loginResponse.put("username", authenticatedUser.getUsername());
        loginResponse.put("token", jwtToken);
        loginResponse.put("tokenExpiresIn", jwtService.getExpirationTime());

        return loginResponse;
    }

}
