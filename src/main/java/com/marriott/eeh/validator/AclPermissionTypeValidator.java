package com.marriott.eeh.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import com.marriott.eeh.validator.constraint.AclPermissionType;

public class AclPermissionTypeValidator extends AbstractValidator implements ConstraintValidator<AclPermissionType, String> {

	/**
	 * The method will initialize the annotated element
	 *
	 * @param constraint the AclPermissionType annotation
	 */
	@Override
	public void initialize(AclPermissionType constraint) {
		ConstraintValidator.super.initialize(constraint);
	}

	/**
	 * The method handle a validation logic for the annotated element
	 *
	 * @param value   the AclPermissionType
	 * @param context the constraint validator constraintValidatorContext
	 * @return boolean true|false
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (!StringUtils.hasText(value)) {
			replaceTemplate(context, "NotBlank");
			return false;
		}
		return !org.apache.kafka.common.acl.AclPermissionType.fromString(value).isUnknown();
	}
}
