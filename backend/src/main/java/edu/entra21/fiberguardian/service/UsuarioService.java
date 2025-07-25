package edu.entra21.fiberguardian.service;

import java.util.Optional;

import edu.entra21.fiberguardian.exception.exception.*;
import edu.entra21.fiberguardian.model.Setor;
import edu.entra21.fiberguardian.model.Turno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import edu.entra21.fiberguardian.model.Role;
import edu.entra21.fiberguardian.model.Usuario;
import edu.entra21.fiberguardian.repository.UsuarioRepository;

@Service
@Transactional(readOnly = true) // padrão: todos os métodos SÃO transacionais, mas SÓ de leitura
public class UsuarioService {
	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private static final BadCredentialsException CREDENCIAIS_INVALIDAS =
			new BadCredentialsException("Credenciais inválidas");

	public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
						  AuthenticationManager authenticationManager) {
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;

	}

	public Page<Usuario> listarPaginado(Pageable pageable) {
		return usuarioRepository.findAll(pageable);
	}

	public boolean estaBloqueado(String email) {
		Usuario usuario = buscarPorEmailObrigatorio(email);
		return !usuario.getAtivo();
	}

	@Transactional
	public Usuario cadastrarNovoUsuario(Usuario usuario) {

		// codigo defensivo, validation no controller já
		// barra email vazio.
		String email = usuario.getEmail();

		if (email == null || email.trim().isEmpty()) {
			throw new NegocioException("E-mail do usuário é obrigatório.");
		}

		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(email);

		// se usuario novo Id=null e achou email no banco, não pode haver emails iguais
		// - INCLUSAO
		// se existe usuario e o id é diferente do usuario atual, não pode haver emails
		// iguais - ALTERACAO
		boolean emailCadastradoPorOutro = usuarioExistente.isPresent()
				&& (usuario.getId() == null || !usuarioExistente.get().getId().equals(usuario.getId()));

		if (emailCadastradoPorOutro) {
			throw new NegocioException(String.format("Já existe usuário cadastrado com o e-mail %s", email));
		}

		String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
		usuario.setSenha(senhaCriptografada);

		return usuarioRepository.save(usuario);
	}

	public boolean senhaCorreta(String senhaInformada, Usuario usuario) {
		return passwordEncoder.matches(senhaInformada, usuario.getSenha());
	}

	public boolean senhaRepetida(String novaSenha, Usuario usuario) {
		return (passwordEncoder.matches(novaSenha, usuario.getSenha()));
	}

	@Transactional
	public void atualizarSenha(String email, String novaSenha, String senhaAtual) {

		Usuario usuario = buscarPorEmailObrigatorio(email);

		if (senhaRepetida(novaSenha, usuario)) {
			throw new NegocioException("A nova senha não pode ser igual à anterior.");
		}

		if (!senhaCorreta(senhaAtual, usuario)) {
			throw new UsuarioSenhaIncorretaException("Senha inválida!");
		}

		String senhaCriptografada = passwordEncoder.encode(novaSenha);
		usuario.setSenha(senhaCriptografada);
		usuarioRepository.save(usuario);
	}

	@Transactional
	public Usuario alterarDadosUsuario(String emailUsuario, String novoNome, String novoSetor,String novoTurno) {
		Usuario usuario = buscarPorEmailObrigatorio(emailUsuario);

		usuario.setNome(novoNome);
		usuario.setSetor(Setor.valueOf(novoSetor));
		usuario.setTurno(Turno.valueOf(novoTurno));

		return usuarioRepository.save(usuario);
	}

	public Usuario buscarPorEmailObrigatorio(String email) {
		return usuarioRepository.findByEmail(email).orElseThrow(() -> new UsuarioNaoEncontradoException(email));
	}

	public boolean existeEmailCadastrado(String email) {
		if (usuarioRepository.existsByEmail(email)) {
			throw new EntidadeEmUsoException("Já existe um usuário com o e-mail informado.");
		}
		return true;
	}

	@Transactional
	public void ativarUsuario(String emailAutenticado, String emailUsuario) {
		validarMudancaStatus(emailAutenticado, emailUsuario);
		Usuario usuario = buscarPorEmailObrigatorio(emailUsuario);
		usuario.setAtivo(true);
		usuarioRepository.save(usuario);
	}

	@Transactional
	public void inativarUsuario(String emailAutenticado, String emailUsuario) {
		validarMudancaStatus(emailAutenticado, emailUsuario);
		Usuario usuario = buscarPorEmailObrigatorio(emailUsuario);
		usuario.setAtivo(false);
		usuarioRepository.save(usuario);
	}

	private void validarMudancaStatus(String emailAutenticado, String emailUsuario) {
		if (emailAutenticado.equalsIgnoreCase(emailUsuario)) {
			throw new UsuarioAutoMudancaStatusException("Usuário não pode alterar seu próprio status");
		}
	}

	public Authentication autenticarSupervisor(String email, String senha) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(email, senha)
		);

		boolean ehSupervisor = authentication.getAuthorities().stream()
				.anyMatch(auth -> auth.getAuthority().equals(Role.ADMIN.getAuthority()));

		if (!ehSupervisor) {
			throw CREDENCIAIS_INVALIDAS;
		}

		return authentication;
	}

	@Transactional
	public void resetarSenha(String email, String novaSenha, String repeteSenha) {

		Usuario usuario = buscarPorEmailObrigatorio(email);

		if (!novaSenha.equals(repeteSenha)) {
			throw new NegocioException("As senhas não são iguais. Verifique.");
		}

		String senhaCriptografada = passwordEncoder.encode(novaSenha);
		usuario.setSenha(senhaCriptografada);
		usuarioRepository.save(usuario);
	}
}
