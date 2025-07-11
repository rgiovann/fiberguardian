package edu.entra21.fiberguardian.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Documented
@Constraint(validatedBy = {}) // Usaremos só composição, sem lógica customizada
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@NotBlank(message = "A senha é obrigatória")
@Size(max = 20, message = "Senha deve ter até 20 caracteres")
public @interface SenhaValida {

	String message() default "Senha inválida";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
