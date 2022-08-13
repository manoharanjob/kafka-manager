package com.marriott.eeh.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.marriott.eeh.validator.constraint.EnvType;

public class EnvTypeValidator extends AbstractValidator implements ConstraintValidator<EnvType, String> {

	private boolean exists;

	/**
	 * The method will initialize the annotated element
	 *
	 * @param constraint the EnvType annotation
	 */
	@Override
	public void initialize(EnvType constraint) {
		ConstraintValidator.super.initialize(constraint);
	}

	/**
	 * The method handle a validation logic for the annotated element
	 *
	 * @param value   the EnvType
	 * @param context the constraint validator constraintValidatorContext
	 * @return boolean true|false
	 */
	@Override
	public boolean isValid(String env, ConstraintValidatorContext context) {
		if (env != null) {
			return true;
		}
//		replaceTemplate(context, "");
		return false;
	}
}
