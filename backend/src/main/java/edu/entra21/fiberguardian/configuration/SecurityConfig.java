package edu.entra21.fiberguardian.configuration;

import java.util.List;

import edu.entra21.fiberguardian.model.Role;
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
import org.springframework.web.filter.ForwardedHeaderFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
	private final CustomUserDetailsService userDetailsService;
	private static final String[] ROLE_ADMIN_USUARIO = {
			Role.ADMIN.getAuthority(),
			Role.USUARIO.getAuthority()
	};

	private static final String[] ROLE_LISTAGEM = {
			Role.ADMIN.getAuthority(),
			Role.USUARIO.getAuthority(),
			Role.LABORATORIO.getAuthority(),
			Role.ENG_LAB.getAuthority()
	};

	private static final String[] ROLE_ADM_LABORATORIO_ENG_LAB = {
			Role.ADMIN.getAuthority(),
			Role.LABORATORIO.getAuthority(),
			Role.ENG_LAB.getAuthority()
	};

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
						.ignoringRequestMatchers(
								"/csrf-token",         // Para obter o token CSRF inicial
								"/sessao/valida"
						)
				)
				// Força HTTPS
				.requiresChannel(channel -> channel.anyRequest().requiresSecure())
				// Configura autorização
				.authorizeHttpRequests(authz -> authz.requestMatchers(HttpMethod.POST, "/api/fg-login").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/csrf-token").permitAll()
						.requestMatchers(HttpMethod.POST, "/api/usuarios/validar-admin").permitAll() // checar sem token-csrf

						// ESPECÍFICAS PRIMEIRO - antes das regras gerais
						.requestMatchers(HttpMethod.GET, "/api/fornecedores/list/recebimento/**").hasAnyAuthority(ROLE_LISTAGEM)
						.requestMatchers(HttpMethod.GET, "/api/notas-fiscais/list/por_fornecedor/**").hasAnyAuthority(ROLE_LISTAGEM)
						.requestMatchers(HttpMethod.GET, "/api/item-notas-fiscais/list/**").hasAnyAuthority(ROLE_LISTAGEM)
						.requestMatchers(HttpMethod.GET, "/api/notas-fiscais/list/**").hasAnyAuthority(ROLE_LISTAGEM)
						.requestMatchers(HttpMethod.GET, "/api/usuarios/lista-usuario-por-role").hasAnyAuthority(ROLE_LISTAGEM)

						.requestMatchers(HttpMethod.POST, "/api/usuarios/reset-senha").hasAuthority(Role.ADMIN.getAuthority())
						.requestMatchers(HttpMethod.POST, "/api/usuarios").hasAuthority(Role.ADMIN.getAuthority())
						.requestMatchers(HttpMethod.GET, "/api/usuarios").hasAuthority(Role.ADMIN.getAuthority())
						.requestMatchers(HttpMethod.PUT, "/api/ativo").hasAuthority(Role.ADMIN.getAuthority())
						.requestMatchers(HttpMethod.DELETE, "/api/ativo").hasAuthority(Role.ADMIN.getAuthority())

						// Padrão seguro para cada recurso
 						.requestMatchers(HttpMethod.POST, "/api/fornecedores/**").hasAnyAuthority(ROLE_ADMIN_USUARIO)
						.requestMatchers(HttpMethod.PUT, "/api/fornecedores/**").hasAnyAuthority(Role.ADMIN.getAuthority())
						.requestMatchers(HttpMethod.DELETE, "/api/fornecedores/**").hasAnyAuthority(Role.ADMIN.getAuthority())
						.requestMatchers(HttpMethod.GET, "/api/fornecedores/**").hasAnyAuthority(ROLE_ADMIN_USUARIO)

						.requestMatchers(HttpMethod.POST, "/api/produtos/**").hasAnyAuthority(ROLE_ADMIN_USUARIO)
						.requestMatchers(HttpMethod.PUT, "/api/produtos/**").hasAnyAuthority(Role.ADMIN.getAuthority())
						.requestMatchers(HttpMethod.DELETE, "/api/produtos/**").hasAnyAuthority(Role.ADMIN.getAuthority())
						.requestMatchers(HttpMethod.GET, "/api/produtos/**").hasAnyAuthority(ROLE_ADMIN_USUARIO)

						.requestMatchers(HttpMethod.POST, "/api/notas-fiscais/**").hasAnyAuthority(ROLE_ADMIN_USUARIO)
						.requestMatchers(HttpMethod.PUT, "/api/notas-fiscais/**").hasAnyAuthority(Role.ADMIN.getAuthority())
						.requestMatchers(HttpMethod.DELETE, "/api/notas-fiscais/**").hasAnyAuthority(Role.ADMIN.getAuthority())
						.requestMatchers(HttpMethod.GET, "/api/notas-fiscais/**").hasAnyAuthority(ROLE_ADMIN_USUARIO)

						.requestMatchers(HttpMethod.POST, "/api/laboratorios/**").hasAnyAuthority(ROLE_ADM_LABORATORIO_ENG_LAB)
						.requestMatchers(HttpMethod.PUT, "/api/laboratorios/**").hasAnyAuthority(ROLE_ADM_LABORATORIO_ENG_LAB)
						.requestMatchers(HttpMethod.DELETE, "/api/laboratorios/**").hasAnyAuthority(ROLE_ADM_LABORATORIO_ENG_LAB)
						.requestMatchers(HttpMethod.GET, "/api/laboratorios/**").hasAnyAuthority(ROLE_ADM_LABORATORIO_ENG_LAB)


						.requestMatchers(HttpMethod.POST, "/api/fg-logout").authenticated()
						.requestMatchers(HttpMethod.POST, "/api/usuarios/alterar-senha").authenticated()
						.requestMatchers(HttpMethod.GET, "/api/usuarios/buscar-por-email").authenticated().anyRequest()
						.authenticated())
				// Configura sessões
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
						.sessionFixation().migrateSession().maximumSessions(1).maxSessionsPreventsLogin(false))
				// Desativa form login
				.formLogin(form -> form.disable()).logout(logout -> logout.logoutUrl("/spring-logout"));

		return http.build();
	}

	// 07.09.2025
	@Bean
	ForwardedHeaderFilter forwardedHeaderFilter() {
		return new ForwardedHeaderFilter();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of("https://localhost:443", // Origem do frontend via Caddy
				"https://localhost:8080", // Para testes diretos no frontend
				"https://127.0.0.1:8080", //  Para testes diretos no frontend
				"https://fiberguardian.local", // docker-compose virtual box
				"https://20.80.234.41", // vm azure
				"https://fiberguardian.duckdns.org"

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
