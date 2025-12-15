package br.com.mercado_souto.model.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.mercado_souto.model.acess.Role;
import br.com.mercado_souto.model.acess.User;
import br.com.mercado_souto.model.acess.UserService;
import br.com.mercado_souto.model.client.Client;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;


   
    private final ObjectMapper objectMapper = new ObjectMapper();

   @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name"); 

       
        Client client = userService.findOrCreateOAuthClient(email, name); 
        
        User authenticatedUser = client.getUser();
    
        Long clientId = client.getId(); 

        
        String jwtToken = jwtService.generateToken(authenticatedUser);

        
        Map<Object, Object> loginResponse = new HashMap<>();
        loginResponse.put("clientId", clientId);
        loginResponse.put("token", jwtToken);
        loginResponse.put("tokenExpiresIn", jwtService.getExpirationTime());
        loginResponse.put("roles", authenticatedUser.getRoles()
                .stream()
                .map(Role::getName)
                .toList());

        
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(loginResponse));

       
    }
}
