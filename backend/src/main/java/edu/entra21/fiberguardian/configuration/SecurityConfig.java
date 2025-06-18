package edu.entra21.fiberguardian.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import edu.entra21.fiberguardian.service.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @SuppressWarnings("deprecation")
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Configura CORS para frontend estático
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // Configura CSRF com cookie acessível pelo frontend
                .csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringRequestMatchers("/fiberguardian/login") // Ignora CSRF para login inicial
                )
                // Força HTTPS
                .requiresChannel(channel -> channel.anyRequest().requiresSecure())
                // Configura autorização
                .authorizeHttpRequests(authz -> authz
                .requestMatchers(HttpMethod.POST, "/fiberguardian/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/fiberguardian/csrf-token").permitAll() // Endpoint para obter CSRF
                .requestMatchers(HttpMethod.GET, "/public/**").permitAll()
                .anyRequest().authenticated()
                )
                // Configura sessões
                .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .sessionFixation().migrateSession() // Protege contra session fixation
                )
                // Desativa form login
                .formLogin(form -> form.disable())
                // Configura logout
                .logout(logout -> logout
                .logoutUrl("/fiberguardian/logout")
                .logoutSuccessHandler((req, res, auth) -> res.setStatus(HttpServletResponse.SC_OK))
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "XSRF-TOKEN")
                );

        // Configura atributos do cookie JSESSIONID
        /*
        // O Spring Boot com Tomcat já aplica essas configurações 
        // automaticamente a partir do application.properties
        http.sessionManagement(session -> session
                .sessionAuthenticationStrategy((authentication, request, response) -> {
                    request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
                    response.setHeader("Set-Cookie", "JSESSIONID=" + request.getSession().getId()
                            + "; Secure; HttpOnly; SameSite=Strict");
                })
        );
         */
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("https://localhost:3000"); // Ajuste para o domínio do frontend
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true); // Permite envio de cookies
        configuration.setMaxAge(3600L); // Cache de preflight por 1 hora
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
