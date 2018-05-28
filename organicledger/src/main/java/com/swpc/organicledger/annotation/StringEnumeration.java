package com.swpc.organicledger.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.swpc.organicledger.validator.StringEnumerationValidator;

@Documented
@Constraint(validatedBy = StringEnumerationValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER, ElementType.CONSTRUCTOR })
@Retention(RetentionPolicy.RUNTIME)
public @interface StringEnumeration {

	String message() default "{usertype.invalid}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	Class<? extends Enum<?>> enumClass();

}