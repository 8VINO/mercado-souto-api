package br.com.mercado_souto.config;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.mercado_souto.model.security.JwtAuthenticationFilter;
import br.com.mercado_souto.model.security.OAuth2AuthenticationSuccessHandler;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
 @Autowired 
    private AuthenticationProvider authenticationProvider;
    
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    
    
    @Autowired
    private OAuth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler;

   
    public SecurityConfiguration() {
      
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(c -> c.disable())
            .authorizeHttpRequests(authorize -> authorize

               
                .requestMatchers("/oauth2/**", "/login/oauth2/**").permitAll()

                .requestMatchers(HttpMethod.POST, "/api/client").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/seller").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/login").permitAll()
                
                .requestMatchers(HttpMethod.GET, "/api/product").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/product/*").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/product/search/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/product/by-seller/*").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/product/by-category/*").permitAll()

                .requestMatchers(HttpMethod.GET, "/api/category").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/category/*").permitAll()
                
                .requestMatchers(HttpMethod.GET, "/api-docs/*").permitAll()
                .requestMatchers(HttpMethod.GET, "/swagger-ui/*").permitAll()

                .requestMatchers("/images/**").permitAll()
                
                .anyRequest().authenticated()
                
            )
            
            .oauth2Login(oauth2 -> oauth2
                
                .successHandler(oauth2AuthenticationSuccessHandler) 
                
               
                .failureHandler((request, response, exception) -> 
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Login com Google falhou.")
                )
            )
            .sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )            
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        // configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(false);
        // configuration.setAllowCredentials(true);
    
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);    
        return source;
    }

}
