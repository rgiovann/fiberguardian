package edu.entra21.fiberguardian.service;

import edu.entra21.fiberguardian.exception.exception.FornecedorNaoEncontradoException;
import edu.entra21.fiberguardian.exception.exception.EntidadeEmUsoException;
import edu.entra21.fiberguardian.exception.exception.NegocioException;
import edu.entra21.fiberguardian.model.Fornecedor;
import edu.entra21.fiberguardian.model.NotaFiscal;
import edu.entra21.fiberguardian.repository.FornecedorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)  //  todos os métodos sao SÓ de leitura, a menos declarado o contrario
public class FornecedorService {
    private static final Logger logger = LoggerFactory.getLogger(FornecedorService.class);
    private final FornecedorRepository fornecedorRepository;
    private static final String MSG_FORNECEDOR_EM_USO = "Fornecedor de código %s não pode ser removido, pois está em uso.";

    public FornecedorService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    @Transactional
    public Fornecedor salvar(Fornecedor fornecedor) {

        return fornecedorRepository.save(fornecedor);
    }

    public Fornecedor buscarPorCNPJObrigatorio(String cnpj) {
        return fornecedorRepository.findByCnpj(cnpj.trim()).orElseThrow(() -> new FornecedorNaoEncontradoException(cnpj));
    }

    public Optional<Fornecedor> buscarPorCNPJOpcional(String cnpj) {
        return fornecedorRepository.findByCnpj(cnpj.trim());
    }

    public List<Fornecedor> listar() {

        return fornecedorRepository.findAll();

    }

    public Page<Fornecedor> listarPaginado(Pageable pageable) {
        return fornecedorRepository.findAll(pageable);
    }

    public List<Fornecedor> listarFiltroPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return fornecedorRepository.findAll();
        }
        return fornecedorRepository.findByNomeFornecedorContainingIgnoreCase(nome.trim());
    }


    @Transactional
    public void excluir(String cnpj) {
        try {
            buscarPorCNPJObrigatorio(cnpj);

            fornecedorRepository.deleteByCnpj(cnpj);

            fornecedorRepository.flush();

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_FORNECEDOR_EM_USO, cnpj));
        }

    }

    // Validação para inserção (novo registro)
    public void validarInsercao(String novoCnpj, String novoNome) {
        validarDuplicidade(null, novoCnpj, novoNome);
    }

    public void validarAlteracao(String cnpjAtual, String novoCnpj, String novoNome) {
        Fornecedor fornecedorAtual = buscarPorCNPJObrigatorio(cnpjAtual);
        validarDuplicidade(fornecedorAtual.getId(), novoCnpj, novoNome);
    }

    public void validaFornecedor(String cnpj){
        if (!fornecedorRepository.existsFornecedorByCnpj(cnpj)) {
            throw new FornecedorNaoEncontradoException(cnpj);
        }
    }

    // Metodo privado auxiliar que valida duplicidade de CNPJ e nome,
    // ignorando o registro com id em 'idIgnorar' (se presente)
    private void validarDuplicidade(Long idIgnorar, String cnpj, String nome) {
        fornecedorRepository.findByCnpj(cnpj).ifPresent(f -> {
            if (idIgnorar == null || !idIgnorar.equals(f.getId())) {
                throw new NegocioException(
                        String.format("Já existe fornecedor com o CNPJ %s", cnpj)
                );
            }
        });

        fornecedorRepository.findByNomeFornecedorIgnoreCase(nome).ifPresent(f -> {
            if (idIgnorar == null || !idIgnorar.equals(f.getId())) {
                throw new NegocioException(
                        String.format("Já existe fornecedor com o nome '%s'", nome)
                );
            }
        });
    }

    /**
     * Autocomplete por código parcial do fornecedor.
     * Retorna no máximo 20 resultados.
     */
    public List<Fornecedor> buscaTop20ByNomeFornecedorContendoStringIgnoraCase(String nomeFornecedor) {
        if (nomeFornecedor == null || nomeFornecedor.isBlank()) {
            return Collections.emptyList();
        }
        return fornecedorRepository.findTop20ByNomeFornecedorContainingIgnoreCase(nomeFornecedor);
    }

}
