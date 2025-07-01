package edu.entra21.fiberguardian.controller;

import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.entra21.fiberguardian.dto.UsuarioLoginResponseDto;
import edu.entra21.fiberguardian.input.UsuarioAutenticadoInput;
import edu.entra21.fiberguardian.model.Usuario;
import edu.entra21.fiberguardian.model.UsuarioAutenticado;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/fiberguardian")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    public AuthController(AuthenticationManager authenticationManager, ModelMapper modelMapper) {
        this.authenticationManager = authenticationManager;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Validated UsuarioAutenticadoInput loginRequest, HttpServletRequest request) {
        System.out.println("JSESSIONID recebido: " + request.getSession(false).getId()); // Log para depuração
        System.out.println("Token CSRF esperado: " + request.getAttribute("_csrf")); // Log para depuração

        UsernamePasswordAuthenticationToken authToken
                = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getSenha());

        try {
            Authentication authentication = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            HttpSession session = request.getSession(false);
            if (session == null) {
                // Segurança extra: recusar login se não houver sessão válida (token CSRF foi inválido)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sessão inválida ou expirada");
            }
            System.out.println("Reutilizando sessão existente: " + session.getId());

            UsuarioAutenticado usuarioAutenticado = (UsuarioAutenticado) authentication.getPrincipal();
            Usuario usuario = usuarioAutenticado.getUsuario();

            UsuarioLoginResponseDto responseDto = modelMapper.map(usuario, UsuarioLoginResponseDto.class);

            return ResponseEntity.ok(responseDto);
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
    }
}
