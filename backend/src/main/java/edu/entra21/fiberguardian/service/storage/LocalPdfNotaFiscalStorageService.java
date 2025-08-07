package edu.entra21.fiberguardian.service.storage;

import edu.entra21.fiberguardian.configuration.StorageProperties;
import edu.entra21.fiberguardian.exception.exception.StorageException;
import edu.entra21.fiberguardian.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

import java.nio.file.Files;
import java.nio.file.Path;

public class LocalPdfNotaFiscalStorageService implements PdfNotaFiscalStorageService {
    private StorageProperties storageProperties;
    private static final Logger logger = LoggerFactory.getLogger(LocalPdfNotaFiscalStorageService.class);

    public LocalPdfNotaFiscalStorageService(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    @Override
    public void armazenar(NovoPdfNotaFiscal novoPdfNotaFiscal) {
        try {
            Path arquivoPath = getArquivoPath(novoPdfNotaFiscal.getNomeArquivo());
            logger.info("Salvando arquivo em: {}", arquivoPath.toAbsolutePath());

            FileCopyUtils.copy(novoPdfNotaFiscal.getInputStream(), Files.newOutputStream(arquivoPath));
        } catch (Exception e) {
            logger.error("Erro ao armazenar arquivo", e);
            throw new StorageException("Não foi possível armazenar o arquivo " + novoPdfNotaFiscal.getNomeArquivo() + ".", e);
        }

    }

    @Override
    public void remover(String nomeArquivo) {
        Path arquivoPath = this.getArquivoPath(nomeArquivo);

        try {
            Files.deleteIfExists(arquivoPath);
        } catch (Exception e) {

            throw new StorageException("Não foi possível excluir o arquivo " + arquivoPath.getFileName() + ".", e);

        }

    }

    @Override
    public PdfNotaFiscalRecuperado recuperar(String nomeArquivo) {
        Path arquivoPath = this.getArquivoPath(nomeArquivo);

        try {
            PdfNotaFiscalRecuperado pdfNotaFiscalRecuperado = PdfNotaFiscalRecuperado.builder()
                    .inputStream(Files.newInputStream(arquivoPath)).build();

            return pdfNotaFiscalRecuperado;

        } catch (Exception e) {
            throw new StorageException("Não foi possível recuperar o arquivo " + arquivoPath.getFileName() + ".", e);
        }
    }

    private Path getArquivoPath(String nomeArquivo) {
        return storageProperties.getLocal().getDiretorioFotos().resolve(Path.of(nomeArquivo));
    }
}
