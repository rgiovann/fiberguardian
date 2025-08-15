package edu.entra21.fiberguardian.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.OffsetDateTime;

public class RecebimentoRecenteValidator implements ConstraintValidator<RecebimentoRecente, OffsetDateTime> {
    private int mesesMaximo;

    @Override
    public void initialize(RecebimentoRecente constraintAnnotation) {
        this.mesesMaximo = constraintAnnotation.mesesMaximo();
    }

    @Override
    public boolean isValid(OffsetDateTime value, ConstraintValidatorContext context) {
        if (value == null) return true; // Se precisar obrigar, combine com @NotNull
        OffsetDateTime limite = OffsetDateTime.now().minusMonths(mesesMaximo);
        return !value.isBefore(limite);
    }
}
