package nl.wolfpackit.backend_assessment.web.exception;

import org.apache.commons.lang3.RandomStringUtils;

public class DomainValidationException extends RuntimeException {

    private final String errorCode;

    public DomainValidationException(String message, Object... params) {
        super(params != null && params.length > 0 ? String.format(message, params) : message);
        errorCode = RandomStringUtils.random(8, true, true).toUpperCase();
    }

    public String getErrorCode() {
        return errorCode;
    }
}
