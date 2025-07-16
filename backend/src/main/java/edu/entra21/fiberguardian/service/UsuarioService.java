package edu.entra21.fiberguardian.service;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.entra21.fiberguardian.exception.exception.EntidadeEmUsoException;
import edu.entra21.fiberguardian.exception.exception.NegocioException;
import edu.entra21.fiberguardian.exception.exception.UsuarioNaoEncontradoException;
import edu.entra21.fiberguardian.model.Usuario;
import edu.entra21.fiberguardian.repository.UsuarioRepository;

@Service
public class UsuarioService {
	private static final String MSG_USUARIO_EM_USO = "Usuário de código %d não pode ser removido, pois está em uso.";
	private static final String MSG_SENHA_ATUAL_NAO_PODE_SER_A_MESMA = "Senha nova do usuário %d igual a senha anterior.";
	private static final String MSG_SENHA_ATUAL_INCORRETA = "Senha atual do usuário %d é incorreta.";
	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;

	public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;

	}

	public List<Usuario> listar() {

		return usuarioRepository.findAll();

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

	@Transactional
	public void excluir(Long usuarioId) {
		try {

			usuarioRepository.deleteById(usuarioId);

			usuarioRepository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new UsuarioNaoEncontradoException(usuarioId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_USUARIO_EM_USO, usuarioId));
		}

	}

	@Transactional
	public Usuario alterarNomeUsuario(String novoNome, Usuario usuario){
		usuario.setNome(novoNome);
		return usuarioRepository.save(usuario);
	}

	public Usuario buscarOuFalhar(Long usuarioId) {
		return usuarioRepository.findById(usuarioId).orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
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

}
