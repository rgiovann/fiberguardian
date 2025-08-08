package edu.entra21.fiberguardian.service;

import edu.entra21.fiberguardian.exception.FornecedorNaoEncontrado;
import edu.entra21.fiberguardian.exception.exception.EntidadeEmUsoException;
import edu.entra21.fiberguardian.model.Fornecedor;
import edu.entra21.fiberguardian.model.Usuario;
import edu.entra21.fiberguardian.repository.FornecedorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // padrão: todos os métodos SÃO transacionais, mas SÓ de leitura
public class FornecedorService {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);
    private final FornecedorRepository fornecedorRepository;
    private static final String MSG_FORNECEDOR_EM_USO = "Fornecedor de código %d não pode ser removido, pois está em uso.";

    public FornecedorService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }



    @Transactional
    public Fornecedor salvar(Fornecedor fornecedor) {

        return fornecedorRepository.save(fornecedor);
    }

    public Fornecedor buscarPorCNPJObrigatorio(String cnpj) {
        return fornecedorRepository.findByCnpj(cnpj.trim()).orElseThrow(() -> new FornecedorNaoEncontrado(cnpj));
    }

    public List<Fornecedor> listar() {

        return fornecedorRepository.findAll();

    }


    @Transactional
    public void excluir(String cnpj) {
        try {

            fornecedorRepository.deleteByCnpj(cnpj);

            fornecedorRepository.flush();

        } catch (EmptyResultDataAccessException e) {
            throw new FornecedorNaoEncontrado(cnpj);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_FORNECEDOR_EM_USO, cnpj));
        }

    }
}
