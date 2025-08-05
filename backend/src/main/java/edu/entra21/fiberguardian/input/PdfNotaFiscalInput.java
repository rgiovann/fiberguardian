package edu.entra21.fiberguardian.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PdfNotaFiscalInput {

    @NotNull
    MultipartFile arquivo;
    @NotBlank
    String descricao;
}
