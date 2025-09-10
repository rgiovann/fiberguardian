package edu.entra21.fiberguardian.service;

import edu.entra21.fiberguardian.dto.RelatorioTesteDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RelatorioService {

    public byte[] gerarRelatorioPDF(RelatorioTesteDTO dados) {
        try {
            // Carrega o template compilado (.jasper)
            InputStream templateStream = new ClassPathResource("/reports/fiberguardian-01.jasper").getInputStream();

            // Parâmetros básicos
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("TITULO", "Relatório Testes de Laboratório");

            // Fonte de dados (lista com um elemento)
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(List.of(dados));

            // Gera o relatório
            JasperPrint jasperPrint = JasperFillManager.fillReport(templateStream, parametros, dataSource);

            // Exporta para PDF
            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar relatório: " + e.getMessage(), e);
        }
    }
}
