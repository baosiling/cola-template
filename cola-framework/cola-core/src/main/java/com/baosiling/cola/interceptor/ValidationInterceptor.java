package com.baosiling.cola.interceptor;

import com.alibaba.cola.dto.Command;
import com.baosiling.cola.command.CommandInterceptorI;
import com.baosiling.cola.command.PreInterceptor;
import com.baosiling.cola.exception.BizException;
import com.baosiling.cola.logger.Logger;
import com.baosiling.cola.logger.LoggerFactory;
import com.baosiling.cola.validator.AbstractValidationInterceptor;
import com.baosiling.cola.validator.ColaMessageInterpolator;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@PreInterceptor
public class ValidationInterceptor extends AbstractValidationInterceptor implements CommandInterceptorI {

    private static Logger logger = LoggerFactory.getLogger(ValidationInterceptor.class);

    //Enable fail fast, which will improve performance
    private ValidatorFactory factory = Validation.byProvider(HibernateValidator.class).configure().failFast(true)
            .messageInterpolator(new ColaMessageInterpolator()).buildValidatorFactory();

    @Override
    protected void doValidation(Object target) {
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(target);
        constraintViolations.forEach(violation->{
            logger.debug("Field: " + violation.getPropertyPath() + " Message: " + violation.getMessage());
            throw new BizException(violation.getPropertyPath() + " " + violation.getMessage());
        });
    }

    @Override
    public void preIntercept(Command command) {
        super.validate(command);
    }
}
