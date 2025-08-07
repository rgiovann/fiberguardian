package edu.entra21.fiberguardian.controller;

import edu.entra21.fiberguardian.assembler.PdfNotaFiscalDtoAssembler;
import edu.entra21.fiberguardian.dto.PdfNotaFiscalDto;
import edu.entra21.fiberguardian.exception.exception.EntidadeNaoEncontradaException;
import edu.entra21.fiberguardian.input.PdfNotaFiscalInput;
import edu.entra21.fiberguardian.model.NotaFiscal;
import edu.entra21.fiberguardian.model.PdfNotaFiscal;
import edu.entra21.fiberguardian.service.NotaFiscalService;
import edu.entra21.fiberguardian.service.PdfNotalFiscalService;
import edu.entra21.fiberguardian.service.storage.PdfNotaFiscalStorageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/notasfiscais/{pdfNotaFiscalId}/pdf")
public class PdfNotaFiscalController {
    private final PdfNotalFiscalService pdfNotaFiscalservice;
    private final NotaFiscalService notaFiscalService;
    private final PdfNotaFiscalStorageService pdfNotaFiscalStorageService;
    private final PdfNotaFiscalDtoAssembler pdfNotaFiscalDtoAssembler;


    public PdfNotaFiscalController(PdfNotalFiscalService catalogoFotoProdutoService,
                                   NotaFiscalService notaFiscalService,
                                   PdfNotaFiscalStorageService pdfNotaFiscalStorageService, PdfNotaFiscalDtoAssembler pdfNotaFiscalDtoAssembler) {
        this.pdfNotaFiscalservice = catalogoFotoProdutoService;
        this.notaFiscalService = notaFiscalService;
        this.pdfNotaFiscalStorageService = pdfNotaFiscalStorageService;
        this.pdfNotaFiscalDtoAssembler = pdfNotaFiscalDtoAssembler;
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PdfNotaFiscalDto atualizarFoto(@PathVariable Long pdfNotaFiscalId,
                                          @Valid PdfNotaFiscalInput pdfNotaFiscalInput,
                                          @RequestPart(required=true) MultipartFile arquivo) throws IOException {

        NotaFiscal notaFiscal
                = notaFiscalService.buscarOuFalhar(pdfNotaFiscalId);

        // ja vem no request
        //MultipartFile arquivo = fotoProdutoInput.getArquivo();

        PdfNotaFiscal pdfNotaFiscal = new PdfNotaFiscal();
        pdfNotaFiscal.setNotaFiscal(notaFiscal);
        pdfNotaFiscal.setDescricao(pdfNotaFiscalInput.getDescricao());
        pdfNotaFiscal.setContentType(arquivo.getContentType());
        pdfNotaFiscal.setTamanho(arquivo.getSize());
        pdfNotaFiscal.setNomeArquivo(arquivo.getOriginalFilename());

        PdfNotaFiscal fotoSalva = pdfNotaFiscalservice.salvar(pdfNotaFiscal, arquivo.getInputStream());

        return pdfNotaFiscalDtoAssembler.toDto(fotoSalva);

    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PdfNotaFiscalDto  consultarFoto(@PathVariable Long pdfNotaFiscalId ){

        PdfNotaFiscal pdfNotaFiscal = pdfNotaFiscalservice.buscarOuFalhar(pdfNotaFiscalId );

        return pdfNotaFiscalDtoAssembler.toDto(pdfNotaFiscal);

    }

    @GetMapping(produces = MediaType.ALL_VALUE)
    public ResponseEntity<?> servirFoto(@PathVariable Long pdfNotaFiscalId,
                                        @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
        try {

            // debug on
            //System.out.println("DEBUG 0001 ====== >> acceptHeader = " + acceptHeader );
            // debug off

            PdfNotaFiscal pdfNotaFiscal = pdfNotaFiscalservice.buscarOuFalhar(pdfNotaFiscalId);

            // debug on
            //System.out.println("DEBUG 0002 ====== >> fotoProduto.getContentType() = " + fotoProduto.getContentType() );
            // debug off

            MediaType  mediaTypeFoto = MediaType.parseMediaType(pdfNotaFiscal.getContentType());

            List<MediaType> mediaTypeAceitas = MediaType.parseMediaTypes(acceptHeader);

            verificarCompatibilidadeMediaType(mediaTypeFoto,mediaTypeAceitas);

            PdfNotaFiscalStorageService.PdfNotaFiscalRecuperado pdfNotaFiscalRecuperado = pdfNotaFiscalStorageService.recuperar(pdfNotaFiscal.getNomeArquivo());

            if(pdfNotaFiscalRecuperado.temUrl())
            {
                return  ResponseEntity
                        .status(HttpStatus.FOUND)
                        .header(HttpHeaders.LOCATION, pdfNotaFiscalRecuperado.getUrl())
                        .build();
            }
            else
            {
                return ResponseEntity
                        .ok()
                        .contentType(mediaTypeFoto)
                        .body(new InputStreamResource(pdfNotaFiscalRecuperado.getInputStream()));
            }
        }
        catch(EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }



    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirFoto(@PathVariable Long pdfNotaFiscalId)
    {
        PdfNotaFiscal pdfNotaFiscal = pdfNotaFiscalservice.buscarOuFalhar(pdfNotaFiscalId);
        pdfNotaFiscalservice.excluir(pdfNotaFiscal);
    }

    private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto, List<MediaType> mediaTypeAceitas) throws HttpMediaTypeNotAcceptableException
    {

        // debug on
        //System.out.println("DEBUG 0003 ====== >> mediaTypeFoto.getType() = " + mediaTypeFoto.getType() );
        // debug off

        // debug on
        //for (MediaType mt : mediaTypeAceitas) {
        //
        //	 System.out.println("DEBUG 0004 ====== >> for each mt.getType() = " + mt.getType() );
        //
        //}
        // debug off

        boolean compativel = mediaTypeAceitas.stream()
                .anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));

        if(!compativel) {
            throw new HttpMediaTypeNotAcceptableException(mediaTypeAceitas);
        }

    }

}
