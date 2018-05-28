package com.swpc.organicledger.validator;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.swpc.organicledger.annotation.NotEmptyFields;

public class NotEmptyFieldsValidator implements ConstraintValidator<NotEmptyFields, List<String>> {

    @Override
    public void initialize(NotEmptyFields notEmptyFields) {
    }

    @Override
    public boolean isValid(List<String> objects, ConstraintValidatorContext context) {
        return objects.stream().allMatch(nef -> !nef.trim().isEmpty());
    }

}