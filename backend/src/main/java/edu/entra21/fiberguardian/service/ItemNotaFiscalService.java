package edu.entra21.fiberguardian.service;

import edu.entra21.fiberguardian.repository.ItemNotaFiscalRepository;
import org.springframework.stereotype.Service;
import edu.entra21.fiberguardian.model.ItemNotaFiscal;
import edu.entra21.fiberguardian.model.NotaFiscal;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //  todos os métodos sao SÓ de leitura, a menos declarado o contrario
public class ItemNotaFiscalService {

    private final ItemNotaFiscalRepository itemNotaFiscalRepository;
    private final NotaFiscalService notaFiscalService;

    public ItemNotaFiscalService(ItemNotaFiscalRepository itemNotaFiscalRepository, NotaFiscalService notaFiscalService) {
        this.itemNotaFiscalRepository = itemNotaFiscalRepository;
        this.notaFiscalService = notaFiscalService;
    }

    @Transactional(readOnly = true)
    public List<ItemNotaFiscal> buscarItensPorNota(String cnpjFornecedor, String codigoNF) {
        // 1. Buscar a nota fiscal obrigatória
        NotaFiscal nota = notaFiscalService.buscarObrigatorioPorNotaECnpj(cnpjFornecedor, codigoNF);

        // 2. Buscar os itens usando o ID da nota
        return itemNotaFiscalRepository.findByNotaFiscalId(nota.getId());
    }

}
