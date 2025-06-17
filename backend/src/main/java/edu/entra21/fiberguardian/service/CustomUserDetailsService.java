package edu.entra21.fiberguardian.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.entra21.fiberguardian.model.Usuario;
import edu.entra21.fiberguardian.repository.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));

        if (!usuario.getAtivo()) {
            throw new DisabledException("Usuário desativado: " + email);
        }

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha()) // Senha já deve estar criptografada no banco
                .authorities("ROLE_" + usuario.getRole().name()) // Ex: ROLE_ADMIN, ROLE_USUARIO
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!usuario.getAtivo())
                .build();
    }
}
