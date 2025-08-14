package edu.entra21.fiberguardian.service;

import edu.entra21.fiberguardian.exception.exception.NotaFiscalNaoEncontradaException;
import edu.entra21.fiberguardian.exception.exception.PdfNotaFiscalNaoEncontradoException;
import edu.entra21.fiberguardian.model.NotaFiscal;
import edu.entra21.fiberguardian.model.PdfNotaFiscal;
import edu.entra21.fiberguardian.repository.NotaFiscalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true) //  //  todos os métodos sao SÓ de leitura, a menos declarado o contrario

public class NotaFiscalService {

    private final NotaFiscalRepository notaFiscalRepository;
    private final FornecedorService fornecedorService;

    private static final Logger logger = LoggerFactory.getLogger(NotaFiscalService.class);

    public NotaFiscalService(NotaFiscalRepository notaFiscalRepository,
                             FornecedorService fornecedorService) {
        this.notaFiscalRepository = notaFiscalRepository;
        this.fornecedorService = fornecedorService;
    }

    @Transactional
    public NotaFiscal salvar(String cnpj, NotaFiscal notaFiscal) {
        // Regra de negócio específica será implementada depois
        return null;
    }

    @Transactional
    public NotaFiscal atualizar(String cnpj, String codigoNf, NotaFiscal notaFiscalAlterada) {
        // Regra de negócio específica será implementada depois
        return null;
    }

    /**
     * Autocomplete por código parcial da nota fiscal.
     * Retorna no máximo 20 resultados.
     */
    public List<NotaFiscal> buscarPorCodigoNf(String codigoParcialNf) {
        if (codigoParcialNf == null || codigoParcialNf.isBlank()) {
            return Collections.emptyList();
        }
        return notaFiscalRepository.findTop20ByCodigoNfContainingIgnoreCase(codigoParcialNf);
    }

    /**
     * Autocomplete por nome parcial do fornecedor.
     * Retorna no máximo 20 resultados, evitando N+1 no fornecedor.
     */
    public List<NotaFiscal> buscarPorNomeFornecedor(String nomeParcialFornecedor) {
        if (nomeParcialFornecedor == null || nomeParcialFornecedor.isBlank()) {
            return Collections.emptyList();
        }
        return notaFiscalRepository.findTop20ByFornecedorNomeFornecedorContainingIgnoreCase(nomeParcialFornecedor);
    }

    /**
     * Autocomplete por nome parcial do usuário que recebeu a nota.
     * Retorna no máximo 20 resultados, evitando N+1 no usuário.
     */
    public List<NotaFiscal> buscarPorNomeUsuario(String nomeParcialUsuario) {
        if (nomeParcialUsuario == null || nomeParcialUsuario.isBlank()) {
            return Collections.emptyList();
        }
        return notaFiscalRepository.findTop20ByRecebidoPorNomeContainingIgnoreCase(nomeParcialUsuario);
    }

    public NotaFiscal buscarOuFalhar(Long pdfNotaFiscalId ) {

        return  notaFiscalRepository.findNotaFiscalById(pdfNotaFiscalId)
                .orElseThrow(() -> new NotaFiscalNaoEncontradaException(pdfNotaFiscalId));
    }

}

