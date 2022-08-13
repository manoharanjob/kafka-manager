package com.marriott.eeh.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.kafka.common.resource.ResourceType;
import org.springframework.util.StringUtils;

import com.marriott.eeh.validator.constraint.AclResourceType;

public class AclResourceTypeValidator extends AbstractValidator implements ConstraintValidator<AclResourceType, String> {

	/**
	 * The method will initialize the annotated element
	 *
	 * @param constraint the AclResourceType annotation
	 */
	@Override
	public void initialize(AclResourceType constraint) {
		ConstraintValidator.super.initialize(constraint);
	}

	/**
	 * The method handle a validation logic for the annotated element
	 *
	 * @param value   the AclResourceType
	 * @param context the constraint validator constraintValidatorContext
	 * @return boolean true|false
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (!StringUtils.hasText(value)) {
			replaceTemplate(context, "NotBlank");
			return false;
		}
		return !ResourceType.fromString(value).isUnknown();
	}
}
