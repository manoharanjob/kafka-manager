package com.marriott.eeh.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.kafka.common.acl.AclOperation;
import org.springframework.util.StringUtils;

import com.marriott.eeh.validator.constraint.AclOperationType;

public class AclOperationTypeValidator extends AbstractValidator implements ConstraintValidator<AclOperationType, String> {

	/**
	 * The method will initialize the annotated element
	 *
	 * @param constraint the AclOperationType annotation
	 */
	@Override
	public void initialize(AclOperationType constraint) {
		ConstraintValidator.super.initialize(constraint);
	}

	/**
	 * The method handle a validation logic for the annotated element
	 *
	 * @param value   the AclOperationType
	 * @param context the constraint validator constraintValidatorContext
	 * @return boolean true|false
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (!StringUtils.hasText(value)) {
			replaceTemplate(context, "NotBlank");
			return false;
		}
		return !AclOperation.fromString(value).isUnknown();
	}
}
