package edu.entra21.fiberguardian.service;

import edu.entra21.fiberguardian.exception.exception.NegocioException;
import edu.entra21.fiberguardian.exception.exception.ProdutoNaoEncontradoException;
import edu.entra21.fiberguardian.model.Fornecedor;
import edu.entra21.fiberguardian.model.Produto;
import edu.entra21.fiberguardian.repository.FornecedorRepository;
import edu.entra21.fiberguardian.repository.ProdutoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)  //  todos os métodos sao SÓ de leitura, a menos declarado o contrario
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final FornecedorRepository fornecedorRepository;

    private final FornecedorService fornecedorService; // já existente
    private static final Logger logger = LoggerFactory.getLogger(ProdutoService.class);

    public ProdutoService(ProdutoRepository produtoRepository,
                          FornecedorService fornecedorService,
                          FornecedorRepository fornecedorRepository) {
        this.produtoRepository = produtoRepository;
        this.fornecedorService = fornecedorService;
        this.fornecedorRepository = fornecedorRepository;
    }

    @Transactional
    public Produto salvar(String cnpj, Produto produto) {
        fornecedorService.validaFornecedor(cnpj);
        Fornecedor fornecedor = fornecedorService.buscarPorCNPJObrigatorio(cnpj);
        if(produtoRepository.existsByFornecedorCnpjAndCodigo(cnpj,produto.getCodigo())){
            throw new NegocioException("Já existe um produto com esse código para este fornecedor.");
        }

        produto.setFornecedor(fornecedor);
        return produtoRepository.save(produto);
    }

    @Transactional
    public Produto atualizar(String cnpj, String codigoProduto, Produto produtoAlterado) {
        fornecedorService.validaFornecedor(cnpj);
        fornecedorService.buscarPorCNPJObrigatorio(cnpj);
        Produto existente = buscarPorCnpjECodigoObrigatorio(cnpj, codigoProduto);

        // Só valida se código OU descrição foram alterados
        boolean codigoAlterado = !existente.getCodigo().equals(produtoAlterado.getCodigo());
        boolean descricaoAlterada = !existente.getDescricao().equals(produtoAlterado.getDescricao());

        if (codigoAlterado || descricaoAlterada) {
            Optional<Produto> duplicata = produtoRepository.findByCnpjAndCodigoOrDescricao(
                    cnpj,
                    produtoAlterado.getCodigo(),
                    produtoAlterado.getDescricao()
            );

            if (duplicata.isPresent() && !Objects.equals(duplicata.get().getId(), existente.getId())) {
                throw new NegocioException(
                        "Já existe um produto com este código ou descrição para este fornecedor"
                );
            }
        }

        existente.setDescricao(produtoAlterado.getDescricao());
        existente.setCodigo(produtoAlterado.getCodigo());

        return produtoRepository.save(existente);
    }

    public Produto buscarPorCnpjECodigoObrigatorio(String cnpj, String codigoProduto) {
        fornecedorService.validaFornecedor(cnpj);
        return produtoRepository.findByFornecedorCnpjAndCodigoProduto(cnpj, codigoProduto)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(cnpj, codigoProduto));
    }

    @Transactional
    public void excluir(String cnpj, String codigoProduto) {
        fornecedorService.validaFornecedor(cnpj);
            buscarPorCnpjECodigoObrigatorio(cnpj,codigoProduto);
            produtoRepository.deleteByFornecedorCnpjAndCodigo(cnpj,codigoProduto);
            produtoRepository.flush();
    }

    public Page<Produto> listarTodosPaginadoFiltroCnpj(String cnpj, Pageable pageable) {
        fornecedorService.validaFornecedor(cnpj);
        return produtoRepository.findByFornecedorCnpj(cnpj, pageable);
    }

    public List<Produto> listarTodosFiltroCnpj(String cnpj) {
        fornecedorService.validaFornecedor(cnpj);
        return produtoRepository.findByFornecedorCnpj(cnpj);
    }

    /**
     * Autocomplete de produtos filtrando por fornecedor (CNPJ) e parte do codigo do produto.
     */
    public List<Produto> buscaTop20PorDescricao(String cnpjFornecedor, String descricao) {
        if (descricao == null || descricao.isBlank()  ) {
            if (cnpjFornecedor == null || cnpjFornecedor.isBlank()  ) {
                return Collections.emptyList();
            }
            return produtoRepository.findByFornecedorCnpj(cnpjFornecedor.trim());
        }
        // Pesquisa tanto no código quanto na descrição
         return produtoRepository.findTop20ByFornecedor_CnpjAndDescricaoContainingIgnoreCase(
                 cnpjFornecedor.trim(),
                 descricao.trim()  );
    }

}
