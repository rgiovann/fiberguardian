package edu.entra21.fiberguardian.controller;

import edu.entra21.fiberguardian.assembler.PdfNotaFiscalDtoAssembler;
import edu.entra21.fiberguardian.exception.exception.EntidadeNaoEncontradaException;
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

    @GetMapping(
            value = "/{cnpj}/{codigoNF}",
            produces = MediaType.APPLICATION_PDF_VALUE
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
            return ResponseEntity.notFound().build();
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
