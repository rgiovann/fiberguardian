package edu.entra21.fiberguardian.service.query;

import edu.entra21.fiberguardian.model.StatusLaboratorio;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class LaboratorioFilter {

    private LocalDate dataRecebimentoInicio;
    private LocalDate dataRecebimentoFim;
    private String codigoNf;
    private String cnpj;
    private String codigoProduto;
    private String email;
    private StatusLaboratorio status;

    /**
     * Agora é e-mail do usuário que emitiu o laudo (campo da tabela Usuario).
     * Normalizaremos (trim + lower) para comparação case-insensitive.
     */


    public String getCodigoNfTrimmedUpperCase() {
        return (codigoNf == null) ? null : codigoNf.trim().isEmpty() ? null : codigoNf.trim().toUpperCase();
    }

    public String normalizedFornecedorCnpj() {
        if (cnpj == null) return null;
        String s = cnpj.replaceAll("\\D", "").trim();
        return s.isEmpty() ? null : s;
    }

    public String getCodigoProdutoTrimmedUpperCase() {
        return (codigoProduto == null) ? null : codigoProduto.trim().isEmpty() ? null : codigoProduto.trim().toUpperCase();
    }

    /**
     * Retorna email trimado e em lower case, ou null se vazio.
     * Use isto para bind em queries (case-insensitive).
     */
    public String getEmailEmitidoPorNormalizedLowerCase() {
        if (email == null) return null;
        String s = email.trim().toLowerCase();
        return s.isEmpty() ? null : s;
    }
}