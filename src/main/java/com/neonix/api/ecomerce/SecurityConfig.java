 package com.neonix.api.ecomerce;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .anyRequest().permitAll()
        )
        .oauth2ResourceServer(oauth2 -> oauth2.disable()) // 🔹 Desactiva el servidor OAuth2
        .formLogin(login -> login.disable())               // 🔹 Sin formulario de login
        .httpBasic(basic -> basic.disable());              // 🔹 Sin autenticación básica
    return http.build();
}

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //     http
    //         .cors(cors -> cors.configurationSource(corsConfigurationSource()))  // Agrega CORS aquí (nuevo: integra CORS con Security)
    //         .csrf(csrf -> csrf.disable())
    //         .authorizeHttpRequests(authorize -> authorize
    //             .requestMatchers("/api/public/**").permitAll() 
    //             .requestMatchers("/api/categories").permitAll() 
    //             .requestMatchers("/api/products").permitAll()   
    //             .requestMatchers("/").permitAll()  // NUEVO: Permite la raíz / para pruebas (evita 401 en localhost:8080/)
    //             .requestMatchers("/health").permitAll()  // NUEVO: Permite /health si lo usas para checks
    //             .requestMatchers("/actuator/**").permitAll()  // NUEVO: Si usas Spring Actuator
    //             .anyRequest().authenticated()
    //         )
    //         .oauth2ResourceServer(oauth2 -> oauth2
    //             .jwt(jwt -> jwt.decoder(jwtDecoder()))  // Pequeño fix: usa .decoder() en lugar de jwtDecoder()
    //         );

    //     return http.build();
    // }

    @Bean
    public JwtDecoder jwtDecoder() {
        String issuerUri = "https://tu-clerk-dominio.clerk.accounts.dev/.well-known/jwks.json";  // MODIFICA: Pon tu URI real (e.g., Auth0: https://dev-xxx.us.auth0.com/.well-known/jwks.json)
        // Si no tienes JWT aún, comenta esta línea y el .oauth2ResourceServer arriba para dev puro
        // return NimbusJwtDecoder.withJwkSetUri(issuerUri).build();
        return NimbusJwtDecoder.withJwkSetUri(issuerUri).build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:8080"));  // MODIFICA: Agrega 8080 para pruebas desde backend
        
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
       
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "*"));  // MODIFICA: Agrega "*" para flexibilidad en dev
      
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
     
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
} 
