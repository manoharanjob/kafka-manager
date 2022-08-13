package com.marriott.eeh.validator.constraint;

import com.pwc.bcm.core.validators.EmailFormatValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.pwc.bcm.core.validators.constants.ValidationErrorConstants.INVALID_EMAIL_FORMAT;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;

@Constraint(validatedBy = EmailFormatValidator.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailFormat {
    String message() default INVALID_EMAIL_FORMAT;

    boolean exists() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several {@link EmailFormat} annotations on the same element.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @interface List {
        EmailFormat[] value();
    }
}

