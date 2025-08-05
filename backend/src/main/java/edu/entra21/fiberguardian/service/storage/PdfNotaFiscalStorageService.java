package edu.entra21.fiberguardian.service.storage;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
public interface PdfNotaFiscalStorageService {

    void armazenar(NovoPdfNotaFiscal novoPdfNotaFiscal);

    void remover(String nomeArquivo);

    PdfNotaFiscalRecuperado recuperar(String nomeArquivo);

    default String gerarNomeArquivo(String nomeOriginal) {
        return UUID.randomUUID().toString() + "_" + nomeOriginal;
    }

    @Getter
    @Builder
    class NovoPdfNotaFiscal {

        private String nomeArquivo;
        private String contentType;
        private InputStream inputStream;

    }


    @Getter
    @Builder
    class PdfNotaFiscalRecuperado {

        private InputStream inputStream;
        private String url;

        public  boolean temUrl() {
            return url != null;
        }

        public  boolean temInpuStream() {
            return inputStream != null;
        }

    }

    default void substituir(String nomeArquivoAntigo, NovoPdfNotaFiscal novoPdfNotaFiscal) {

        this.armazenar(novoPdfNotaFiscal);

        if(nomeArquivoAntigo != null) {
            this.remover(nomeArquivoAntigo);
        }

    };

}