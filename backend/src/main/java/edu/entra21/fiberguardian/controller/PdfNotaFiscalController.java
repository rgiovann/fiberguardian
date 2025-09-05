package edu.entra21.fiberguardian.controller;

import edu.entra21.fiberguardian.assembler.PdfNotaFiscalDtoAssembler;
import edu.entra21.fiberguardian.exception.exception.EntidadeNaoEncontradaException;
import edu.entra21.fiberguardian.exception.exception.NegocioException;
import edu.entra21.fiberguardian.exception.exception.PdfNotaFiscalNaoEncontradoException;
import edu.entra21.fiberguardian.model.NotaFiscal;
import edu.entra21.fiberguardian.model.PdfNotaFiscal;
import edu.entra21.fiberguardian.service.NotaFiscalService;
import edu.entra21.fiberguardian.service.PdfNotalFiscalService;
import edu.entra21.fiberguardian.service.storage.MultiPartFileStorageService;
import org.springframework.http.HttpHeaders;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController
@RequestMapping(value = "/api/pdf-notas-fiscais/")
public class PdfNotaFiscalController {
    private final PdfNotalFiscalService pdfNotaFiscalservice;
    private final NotaFiscalService notaFiscalService;
    private final MultiPartFileStorageService multiPartFileStorageService;
    private static final Logger logger = LoggerFactory.getLogger(PdfNotaFiscalController.class);




    public PdfNotaFiscalController(PdfNotalFiscalService catalogoFotoProdutoService,
                                   NotaFiscalService notaFiscalService,
                                   MultiPartFileStorageService multiPartFileStorageService, PdfNotaFiscalDtoAssembler pdfNotaFiscalDtoAssembler) {
        this.pdfNotaFiscalservice = catalogoFotoProdutoService;
        this.notaFiscalService = notaFiscalService;
        this.multiPartFileStorageService = multiPartFileStorageService;
    }
    /*
    @GetMapping(
            value = "/{cnpj}/{codigoNF}"

    )    public ResponseEntity<?> servirPdfNotaFiscal(@PathVariable("cnpj") String cnpj,
                                                 @PathVariable("codigoNF") String codigoNF,
                                                 @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
        try {

            NotaFiscal notaFiscal = notaFiscalService.buscarObrigatorioPorNotaECnpj(cnpj,codigoNF);

            PdfNotaFiscal pdfNotaFiscal = pdfNotaFiscalservice.buscarOuFalhar(notaFiscal.getId());

            MediaType  mediaTypeFoto = MediaType.parseMediaType(pdfNotaFiscal.getContentType());

            List<MediaType> mediaTypeAceitas = MediaType.parseMediaTypes(acceptHeader);

            verificarCompatibilidadeMediaType(mediaTypeFoto,mediaTypeAceitas);

            MultiPartFileStorageService.MultiPartFileRecuperado multiPartFileRecuperado = multiPartFileStorageService.recuperar(pdfNotaFiscal.getNomeArquivo());

            if(multiPartFileRecuperado.temUrl())
            {
                return  ResponseEntity
                        .status(HttpStatus.FOUND)
                        .header(HttpHeaders.LOCATION, multiPartFileRecuperado.getUrl())
                        .build();
            }

            else
            {
                return ResponseEntity
                        .ok()
                        .contentType(mediaTypeFoto)
                        .body(new InputStreamResource(multiPartFileRecuperado.getInputStream()));
            }
        }
        catch(EntidadeNaoEncontradaException e) {
            throw new PdfNotaFiscalNaoEncontradoException("Nota fiscal " + codigoNF+ " nao possui pdf");
        }
    }
    */

    @GetMapping("/{cnpj}/{codigoNF}")
    public ResponseEntity<?> servirPdfNotaFiscal(
            @PathVariable("cnpj") String cnpj,
            @PathVariable("codigoNF") String codigoNF,
            @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {

            // 1. Primeiro: verificar se a nota fiscal existe
            NotaFiscal notaFiscal = notaFiscalService.buscarObrigatorioPorNotaECnpj(cnpj, codigoNF);

            // 2. Segundo: verificar se o PDF existe (ponto crítico)
            PdfNotaFiscal pdfNotaFiscal;
            try {
                pdfNotaFiscal = pdfNotaFiscalservice.buscarOuFalhar(notaFiscal.getId());
            } catch (EntidadeNaoEncontradaException e) {
                // Lança exceção customizada imediatamente quando PDF não existe
                throw new PdfNotaFiscalNaoEncontradoException("Nota fiscal " + codigoNF + " nao possui pdf");
            }

            // 3. Terceiro: validar compatibilidade de MediaType
            MediaType mediaTypePdf = MediaType.parseMediaType(pdfNotaFiscal.getContentType());
            List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);

            try {
                verificarCompatibilidadeMediaType(mediaTypePdf, mediaTypesAceitas);
            } catch (HttpMediaTypeNotAcceptableException e) {
                // Esta também - deixa o GlobalExceptionHandler cuidar
                throw e;
            }
            // 4. Quarto: recuperar e servir o arquivo
            MultiPartFileStorageService.MultiPartFileRecuperado arquivo =
                    multiPartFileStorageService.recuperar(pdfNotaFiscal.getNomeArquivo());

            if (arquivo.temUrl()) {
                return ResponseEntity
                        .status(HttpStatus.FOUND)
                        .header(HttpHeaders.LOCATION, arquivo.getUrl())
                        .build();
            } else {
                return ResponseEntity
                        .ok()
                        .contentType(mediaTypePdf)
                        .body(new InputStreamResource(arquivo.getInputStream()));
            }



    }

    private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto, List<MediaType> mediaTypeAceitas) throws HttpMediaTypeNotAcceptableException
    {

        boolean compativel = mediaTypeAceitas.stream()
                .anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));

        if(!compativel) {
            throw new HttpMediaTypeNotAcceptableException(mediaTypeAceitas);
        }

    }

}
