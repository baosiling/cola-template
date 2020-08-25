package com.baosiling.cola.validator;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;

import java.util.Locale;

public class ColaMessageInterpolator extends ResourceBundleMessageInterpolator {

    @Override
    public String interpolate(String message, Context context) {
        return super.interpolate(message, context, Locale.ENGLISH);
    }
}
