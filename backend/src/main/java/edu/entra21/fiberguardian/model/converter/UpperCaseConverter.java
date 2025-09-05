package edu.entra21.fiberguardian.model.converter;

import jakarta.persistence.AttributeConverter;

public class UpperCaseConverter implements AttributeConverter<String, String> {
    @Override
    public String convertToDatabaseColumn(String attribute) {
        return attribute == null ? null : attribute.trim().toUpperCase();
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData;
    }
}
