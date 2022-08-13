package com.marriott.eeh.validator;

import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

public class AbstractValidator {

	protected void replaceTemplate(ConstraintValidatorContext context, String templateKey) {
		HibernateConstraintValidatorContext validator = context.unwrap(HibernateConstraintValidatorContext.class);
		validator.disableDefaultConstraintViolation();
		validator.buildConstraintViolationWithTemplate(templateKey)
				.addConstraintViolation();
	}

	protected void replaceTemplate(ConstraintValidatorContext context, String templateKey, String paramName,
			String paramValue) {
		HibernateConstraintValidatorContext validator = context.unwrap(HibernateConstraintValidatorContext.class);
		validator.disableDefaultConstraintViolation();
		validator.addMessageParameter(paramName, paramValue)
				.buildConstraintViolationWithTemplate(templateKey)
				.addConstraintViolation();
	}

}
