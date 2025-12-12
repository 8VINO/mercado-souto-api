package br.com.mercado_souto.config;
import java.util.Arrays;
import java.util.List;

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

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(c -> c.disable())
            .authorizeHttpRequests(authorize -> authorize

                .requestMatchers(HttpMethod.POST, "/api/client").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/seller").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/login").permitAll()
                
                .requestMatchers(HttpMethod.GET, "/api/product").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/product/*").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/product/by-seller/*").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/product/category/*").permitAll()

                .requestMatchers(HttpMethod.GET, "/api/category").permitAll()
                
                .requestMatchers(HttpMethod.GET, "/api-docs/*").permitAll()
                .requestMatchers(HttpMethod.GET, "/swagger-ui/*").permitAll()

                .requestMatchers("/images/**").permitAll()
                
                .anyRequest().authenticated()
                
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
