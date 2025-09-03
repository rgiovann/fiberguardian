package edu.entra21.fiberguardian.service;

import edu.entra21.fiberguardian.exception.exception.NegocioException;
import edu.entra21.fiberguardian.exception.exception.ProdutoNaoEncontradoException;
import edu.entra21.fiberguardian.model.Fornecedor;
import edu.entra21.fiberguardian.model.Produto;
import edu.entra21.fiberguardian.repository.FornecedorRepository;
import edu.entra21.fiberguardian.repository.ProdutoRepository;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)  //  todos os métodos sao SÓ de leitura, a menos declarado o contrario
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    private final FornecedorService fornecedorService; // já existente
    private static final Logger logger = LoggerFactory.getLogger(ProdutoService.class);

    public ProdutoService(ProdutoRepository produtoRepository,
                          FornecedorService fornecedorService,
                          FornecedorRepository fornecedorRepository) {
        this.produtoRepository = produtoRepository;
        this.fornecedorService = fornecedorService;
    }

    @Transactional(readOnly = false)
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
    public List<Produto> salvarEmLote(@NotBlank(message = "Cnpj é obrigatório") String fornecedorCnpj, List<Produto> produtos) {
        if (produtos == null || produtos.isEmpty()) {
            throw new NegocioException("A lista de produtos não pode ser vazia.");
        }

        // valida e busca fornecedor uma vez só
        fornecedorService.validaFornecedor(fornecedorCnpj);
        var fornecedor = fornecedorService.buscarPorCNPJObrigatorio(fornecedorCnpj);

        // valida duplicidades no payload
        var codigos = new HashSet<String>();
        for (Produto produto : produtos) {
            var codigo = Optional.ofNullable(produto.getCodigo()).orElse("").trim();
            if (!codigos.add(codigo)) {
                throw new NegocioException(
                        String.format("Duplicidade no lote: código %s enviado mais de uma vez.", codigo)
                );
            }
            if (produtoRepository.existsByFornecedorCnpjAndCodigo(fornecedorCnpj, codigo)) {
                throw new NegocioException(
                        String.format("Já existe um produto com o código %s para este fornecedor.", codigo)
                );
            }
            produto.setFornecedor(fornecedor);

        }
        return produtoRepository.saveAll(produtos);
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

    @Transactional(readOnly = false)
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
