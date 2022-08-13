package com.marriott.eeh.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.kafka.common.resource.PatternType;
import org.springframework.util.StringUtils;

import com.marriott.eeh.validator.constraint.AclPatternType;

public class AclPatternTypeValidator extends AbstractValidator implements ConstraintValidator<AclPatternType, String> {

	/**
	 * The method will initialize the annotated element
	 *
	 * @param constraint the AclPatternType annotation
	 */
	@Override
	public void initialize(AclPatternType constraint) {
		ConstraintValidator.super.initialize(constraint);
	}

	/**
	 * The method handle a validation logic for the annotated element
	 *
	 * @param value   the AclPatternType
	 * @param context the constraint validator constraintValidatorContext
	 * @return boolean true|false
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (!StringUtils.hasText(value)) {
			replaceTemplate(context, "NotBlank");
			return false;
		}
		return !PatternType.fromString(value).isUnknown();
	}
}
