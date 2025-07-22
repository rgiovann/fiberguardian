package edu.entra21.fiberguardian.configuration;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import edu.entra21.fiberguardian.service.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
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

	@Bean
	CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler() {
		return new CsrfTokenRequestAttributeHandler();
	}

	@Bean
	CsrfTokenRepository csrfTokenRepository() {
		CookieCsrfTokenRepository repository = CookieCsrfTokenRepository.withHttpOnlyFalse();
		repository.setCookieName("XSRF-TOKEN");
		repository.setHeaderName("X-XSRF-TOKEN");
		repository.setParameterName("_csrf");
		repository.setCookiePath("/");
		repository.setCookieMaxAge(-1); // Session cookie
		repository.setSecure(true); // HTTPS only
		return repository;
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				// Configura CORS para frontend estático
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				// Configura CSRF com cookie acessível pelo frontend
				.addFilterAfter(new SameSiteCookieFilter(), CsrfFilter.class)
				// .csrf(csrf -> csrf.disable())

				.csrf(csrf -> csrf.csrfTokenRepository(csrfTokenRepository())
						.csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
						.ignoringRequestMatchers("/csrf-token", "/sessao/valida") // Ignora CSRF para obter token
				)
				// Força HTTPS
				.requiresChannel(channel -> channel.anyRequest().requiresSecure())
				// Configura autorização
				.authorizeHttpRequests(authz -> authz.requestMatchers(HttpMethod.POST, "/login").permitAll()
						.requestMatchers(HttpMethod.GET, "/csrf-token").permitAll()
						.requestMatchers(HttpMethod.POST, "/usuarios").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/usuarios").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/ativo").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/ativo").hasRole("ADMIN")
						.requestMatchers(HttpMethod.POST, "/logout").authenticated()
						.requestMatchers(HttpMethod.GET, "/usuarios/buscar-por-email").authenticated()
						.anyRequest().authenticated())
				// Configura sessões
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
						.sessionFixation().migrateSession().maximumSessions(1).maxSessionsPreventsLogin(false))
				// Desativa form login
				.formLogin(form -> form.disable())
				.logout(logout -> logout.logoutUrl("/spring-logout"));

		// Desativa form logout
				//.logout(form -> form.disable());

		return http.build();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of("https://localhost:443", // Origem do frontend via Caddy
				"https://localhost:8080", // Para testes diretos no frontend
				"https://127.0.0.1:8080" // Para testes diretos no frontend
		));
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(List.of("X-XSRF-TOKEN", "Content-Type", "Accept"));
		configuration.setAllowCredentials(true);
		configuration.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
