package com.modulo.pix.validation;

import com.modulo.pix.validation.DataValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DataValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateDate {
    String message() default "Data inválida";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
