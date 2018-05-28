package com.swpc.organicledger.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.swpc.organicledger.validator.NotEmptyFieldsValidator;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotEmptyFieldsValidator.class)
public @interface NotEmptyFields {

    String message() default "{authorities.required}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}