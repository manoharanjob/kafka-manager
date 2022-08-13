package com.marriott.eeh.validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.marriott.eeh.validator.constraint.NameFormat;


public class NameFormatValidator extends AbstractValidator implements ConstraintValidator<NameFormat,String> {


    private boolean exists;

    /**
     * The method will initialize the annotated element
     *
     * @param constraintAnnotation the EventId annotation
     */
    @Override
    public void initialize(NameFormat constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * The method handle a validation logic for the annotated element
     *
     * @param value   the EmailFormat
     * @param context the constraint validator constraintValidatorContext
     * @return boolean true|false
     */
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" +
                "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")){
            return true;
        }
//        ValidationUtil.replaceTemplate(constraintValidatorContext,INVALID_EMAIL_FORMAT);
        return false;
    }
}
