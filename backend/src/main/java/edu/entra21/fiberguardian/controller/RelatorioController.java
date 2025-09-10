package edu.entra21.fiberguardian.controller;

import edu.entra21.fiberguardian.dto.RelatorioTesteDTO;
import edu.entra21.fiberguardian.service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @PostMapping("/teste-pdf")
    public ResponseEntity<byte[]> gerarPDF(@RequestBody RelatorioTesteDTO dados) {
        try {
            byte[] pdfBytes = relatorioService.gerarRelatorioPDF(dados);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline", "relatorio_teste.pdf");

            return ResponseEntity.ok().headers(headers).body(pdfBytes);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Endpoint para teste rápido com dados mockados
    @GetMapping("/teste-mock")
    public ResponseEntity<byte[]> gerarPDFTeste() {
        RelatorioTesteDTO dadosTeste = criarDadosTeste();
        return gerarPDF(dadosTeste);
    }

    private RelatorioTesteDTO criarDadosTeste() {
        RelatorioTesteDTO dados = new RelatorioTesteDTO();
        dados.setNumeroNf("123456");
        dados.setCnpj("12.345.678/0001-90");
        dados.setEmpresa("Empresa Teste Ltda");
        dados.setCodigoProduto("PROD001");
        dados.setDescricao("Produto de Teste");
        dados.setNumeroLote("LOTE2024001");
        dados.setEmailEmitidoPor("teste@empresa.com");
        dados.setDataRealizacao(java.time.LocalDate.now());
        dados.setObservacoes("Teste realizado com sucesso");
        dados.setStatus("APROVADO");
        dados.setCvm(new java.math.BigDecimal("12.5"));
        dados.setPontosFinos(5);
        dados.setPontosGrossos(2);
        dados.setNeps(1);
        dados.setPilosidade(new java.math.BigDecimal("3.2"));
        dados.setResistencia(new java.math.BigDecimal("150.0"));
        dados.setAlongamento(new java.math.BigDecimal("8.5"));
        dados.setTituloNe(new java.math.BigDecimal("30.0"));
        dados.setTorcaoTm(850);
        return dados;
    }
}
