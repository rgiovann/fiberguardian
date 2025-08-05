package edu.entra21.fiberguardian.service;

import edu.entra21.fiberguardian.exception.exception.PdfNotaFiscalNaoEncontradoException;
import edu.entra21.fiberguardian.model.PdfNotaFiscal;
import edu.entra21.fiberguardian.repository.NotaFiscalRepository;
import edu.entra21.fiberguardian.service.storage.PdfNotaFiscalStorageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;
@Service
public class PdfNotalFiscalService {
    private final NotaFiscalRepository notaFiscalRepository;

    private final PdfNotaFiscalStorageService notaFiscalStorageService;

    public PdfNotalFiscalService(NotaFiscalRepository cursoRepository, PdfNotaFiscalStorageService notaFiscalStorageService) {
        this.notaFiscalRepository = cursoRepository;
        this.notaFiscalStorageService = notaFiscalStorageService;
    }

    @Transactional
    public PdfNotaFiscal salvar(PdfNotaFiscal pdfNotaFiscal, InputStream dadosArquivo) {

        Long pdfNotaFIscalId = pdfNotaFiscal.getNotaFiscalId();

        String novoNomeArquivo = notaFiscalStorageService.gerarNomeArquivo(pdfNotaFiscal.getNomeArquivo());

        String nomeArquivoExistente = null;

        pdfNotaFiscal.setNomeArquivo(novoNomeArquivo);

        Optional<PdfNotaFiscal> pdfNotaFiscalExistente = notaFiscalRepository.findPdfNotaFiscalById(pdfNotaFIscalId);

        if (pdfNotaFiscalExistente.isPresent()) {

            nomeArquivoExistente = pdfNotaFiscalExistente.get().getNomeArquivo();
            notaFiscalRepository.delete(pdfNotaFiscalExistente.get());
        }

        pdfNotaFiscal = notaFiscalRepository.save(pdfNotaFiscal);
        notaFiscalRepository.flush(); // salva no BD os dados da pdfNotaFiscal, commitando a insercao ANTES de salvar a pdfNotaFiscal.

        PdfNotaFiscalStorageService.NovoPdfNotaFiscal novaFoto = PdfNotaFiscalStorageService.NovoPdfNotaFiscal.builder()
                .nomeArquivo(novoNomeArquivo)
                .inputStream(dadosArquivo)
                .contentType(pdfNotaFiscal.getContentType())
                .build();

        notaFiscalStorageService.substituir(nomeArquivoExistente, novaFoto);

        return pdfNotaFiscal;

    }

    public PdfNotaFiscal buscarOuFalhar(Long pdfNotaFiscalId ) {

        return  notaFiscalRepository.findPdfNotaFiscalById(pdfNotaFiscalId)
                .orElseThrow(() -> new PdfNotaFiscalNaoEncontradoException(pdfNotaFiscalId));
    }

    @Transactional
    public void excluir(PdfNotaFiscal foto) {

        notaFiscalRepository.delete(foto);
        notaFiscalRepository.flush(); // salva no BD os dados da foto, commitando a insercao ANTES de remover a foto.
        notaFiscalStorageService.remover(foto.getNomeArquivo());
    }

}