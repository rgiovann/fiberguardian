package edu.entra21.fiberguardian.service;

import java.util.Optional;

import edu.entra21.fiberguardian.exception.exception.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.entra21.fiberguardian.model.Usuario;
import edu.entra21.fiberguardian.repository.UsuarioRepository;

@Service
@Transactional(readOnly = true) // padrão: todos os métodos SÃO transacionais, mas SÓ de leitura
public class UsuarioService {
	//private static final String MSG_USUARIO_EM_USO = "Usuário de código %d não pode ser removido, pois está em uso.";
	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;

	public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;

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

//	@Transactional
//	public void atualizarSenha(Usuario usuario, String novaSenha, String senhaAtual) {
//
//		// checa senha atual é diferente da nova senha...
//		if (senhaRepetida(novaSenha, usuario)) {
//			throw new NegocioException("A nova senha não pode ser igual à anterior.");
//		}
//		// problema na autenticacao...
//		if (!senhaCorreta(senhaAtual, usuario)) {
//			throw new UsuarioSenhaIncorretaException("Senha invalida!");
//		}
//
//		String senhaCriptografada = passwordEncoder.encode(novaSenha);
//		usuario.setSenha(senhaCriptografada);
//		usuarioRepository.save(usuario);
//	}

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


//	@Transactional
//	public Usuario alterarNomeUsuario(String novoNome, Usuario usuario) {
//		usuario.setNome(novoNome);
//		return usuarioRepository.save(usuario);
//	}

	@Transactional
	public Usuario alterarNomeUsuario(String emailUsuario, String novoNome) {
		Usuario usuario = buscarPorEmailObrigatorio(emailUsuario);
		usuario.setNome(novoNome);
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

//	@Transactional
//	public void mudaStatusUsuario(String emailAutenticado, String emailUsuario, Boolean status) {
//		// Verifica se o usuário está tentando mudar o próprio status
//		if (emailAutenticado.equalsIgnoreCase(emailUsuario)) {
//			throw new UsuarioAutoMudancaStatusException("Usuário não pode alterar seu próprio status");
//		}
//
//		// Verifica se o usuário de destino existe
//		Usuario usuario = buscarPorEmailObrigatorio(emailUsuario);
//		usuario.setAtivo(status);
//		usuarioRepository.save(usuario);
//	}

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

}
