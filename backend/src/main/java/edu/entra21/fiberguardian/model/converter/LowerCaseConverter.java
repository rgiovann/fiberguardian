package edu.entra21.fiberguardian.model.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

@Converter(autoApply = false) // controle explícito onde usar
@Component // se precisar de injeção de dependência
public class LowerCaseConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return attribute == null ? null : attribute.trim().toLowerCase();
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData;
    }
}
