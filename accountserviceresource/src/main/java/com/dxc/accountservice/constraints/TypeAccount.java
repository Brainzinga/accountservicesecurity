package com.dxc.accountservice.constraints;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.regex.Pattern;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {TypeAccount.Validator.class})
public @interface TypeAccount {

        String message()
            default "La cuenta solo puede ser de tipo Personal o Company";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public class Validator implements ConstraintValidator<TypeAccount, String> {
        @Override
        public void initialize(final TypeAccount serial) {
        }

        @Override
        public boolean isValid(final String type, final ConstraintValidatorContext constraintValidatorContext) {
            final String serialNumRegex = "Personal|Company|personal|company";
            if(type!=null) return Pattern.matches(serialNumRegex, type);
            else return false;
        }
    }
}
