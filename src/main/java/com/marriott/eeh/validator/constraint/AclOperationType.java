package com.marriott.eeh.validator.constraint;


import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.marriott.eeh.validator.AclOperationTypeValidator;

@Constraint(validatedBy = AclOperationTypeValidator.class)
@Target({ FIELD, ANNOTATION_TYPE, PARAMETER, TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface AclOperationType {
	
    String message() default "enum";

    boolean exists() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several {@link AclOperationType} annotations on the same element.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ FIELD, ANNOTATION_TYPE, PARAMETER, TYPE_USE })
    @interface List {
    	AclOperationType[] value();
    }
}

