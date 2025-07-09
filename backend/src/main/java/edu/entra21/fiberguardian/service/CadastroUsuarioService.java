package edu.entra21.fiberguardian.service;

import edu.entra21.fiberguardian.exception.exception.EntidadeEmUsoException;
import edu.entra21.fiberguardian.exception.exception.NegocioException;
import edu.entra21.fiberguardian.exception.exception.UsuarioNaoEncontradoException;
import edu.entra21.fiberguardian.model.Usuario;
import edu.entra21.fiberguardian.repository.UsuarioRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import edu.entra21.fiberguardian.exception.exception.n;

public class CadastroUsuarioService {
    private static final String MSG_USUARIO_EM_USO = "Usuário de código %d não pode ser removido, pois está em uso.";
    private static final String MSG_SENHA_ATUAL_NAO_PODE_SER_A_MESMA = "Senha nova do usuário %d igual a senha anterior.";
    private static final String MSG_SENHA_ATUAL_INCORRETA = "Senha atual do usuário %d é incorreta.";
    private final UsuarioRepository usuarioRepository;

    public CadastroUsuarioService(	UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;


    }

    public List<Usuario> listar() {

        return usuarioRepository.findAll();

    }

    @Transactional
    public Usuario salvar(Usuario usuario) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
                                                          // isPresent (OptionaL)

        // se usuario novo Id=null e achou email no banco, não pode haver emails iguais - INCLUSAO
        // se existe usuario e o id é diferente do usuario atual, não pode haver emails iguais - ALTERACAO
        boolean emailCadastradoPorOutro = usuarioExistente.isPresent() &&
                // inclusão (não tem ID) ou alteração (ID diferente)
                (usuario.getId() == null ||
                        !(usuarioExistente.get().getId().equals(usuario.getId())) );

        if (emailCadastradoPorOutro) {
            throw new NegocioException(
                    String.format("Já existe usuário cadastrado com o email %s", usuario.getEmail())
            );
        }

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void excluir(Long usuarioId) {
        try {

            usuarioRepository.deleteById(usuarioId);

            usuarioRepository.flush();

        } catch (EmptyResultDataAccessException e) {
            throw new n(usuarioId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_USUARIO_EM_USO, usuarioId));
        }

    }
    public Usuario buscarOuFalhar(Long usuarioId) {
        return usuarioRepository.findById(usuarioId).orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
    }

}
