package org.finmate.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus()
public class OpenAiApiException extends RuntimeException {
    public OpenAiApiException(String message) {
        super(message);
    }

    public OpenAiApiException(String message, Throwable cause) {
        super(message, cause);
    }
}