package com.marriott.eeh.validator;

public class AbstractValidator {

  public void replaceTemplate(ConstraintValidatorContext context, String templateKey) {
        HibernateConstraintValidatorContext validator = context.unwrap(HibernateConstraintValidatorContext.class);
        validator.disableDefaultConstraintViolation();
        validator.buildConstraintViolationWithTemplate(templateKey).addConstraintViolation();
    }

    public void replaceTemplate(ConstraintValidatorContext context, String templateKey, String paramName, String paramValue) {
        HibernateConstraintValidatorContext validator = context.unwrap(HibernateConstraintValidatorContext.class);
        validator.disableDefaultConstraintViolation();
        validator.addMessageParameter(paramName, paramValue).buildConstraintViolationWithTemplate(templateKey).addConstraintViolation();
    }
  
}
