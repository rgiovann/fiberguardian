package edu.entra21.fiberguardian.service;

import edu.entra21.fiberguardian.exception.exception.NegocioException;
import edu.entra21.fiberguardian.repository.EngenhariaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)  //  todos os métodos sao SÓ de leitura, a menos declarado o contrario
public class EngenhariaService {
    private final EngenhariaRepository engenhariaRepository;

    public EngenhariaService(EngenhariaRepository engenhariaRepository) {
        this.engenhariaRepository = engenhariaRepository;
    }

    public boolean existeLaboratorioAssociado(Long laboratorioId) {
        return engenhariaRepository.existsByTesteLaboratorioId(laboratorioId);
    }

}
