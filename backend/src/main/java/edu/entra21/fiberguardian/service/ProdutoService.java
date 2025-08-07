package edu.entra21.fiberguardian.service;

import edu.entra21.fiberguardian.model.Produto;
import edu.entra21.fiberguardian.repository.ProdutoRepository;
import edu.entra21.fiberguardian.repository.UsuarioRepository;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProdutoService {
    private final ProdutoRepository produtoRepository;


    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll(Sort.by("nome")); // exemplo com ordenação
    }
}
