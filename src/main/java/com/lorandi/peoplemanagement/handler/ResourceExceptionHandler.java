package com.lorandi.peoplemanagement.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.lorandi.peoplemanagement.helper.MessageHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.ValidationException;
import java.util.List;

import static com.lorandi.peoplemanagement.exception.ErrorCodeEnum.ERROR_DATE_FORMAT;
import static com.lorandi.peoplemanagement.exception.ErrorCodeEnum.ERROR_GENERIC_EXCEPTION;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ResourceExceptionHandler {

    private final MessageHelper messageHelper;

    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    void exceptionHandler(ValidationException e) {
        throw new ResponseStatusException(BAD_REQUEST, e.getMessage(), e);
    }

    @ResponseBody
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorDTO> handlerResponseStatus(ResponseStatusException e, HttpServletRequest request) {
        return new ResponseEntity<>(ErrorDTO.builder()
                .message(e.getReason())
                .error(e.getStatus().getReasonPhrase())
                .path(request.getRequestURI())
                .status(e.getStatus().value())
                .build(), e.getStatus());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandardError> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e,
            HttpServletRequest request) {
        HttpStatus status = BAD_REQUEST;
        var message = messageHelper.get(ERROR_GENERIC_EXCEPTION, e);

        if(((InvalidFormatException)e.getCause()).getTargetType().getName().equals("java.time.LocalDate")) {
            message = messageHelper.get(ERROR_DATE_FORMAT, e);
        }
        log.error(request.getRequestURI(), e);
        return ResponseEntity
                .status(status)
                .body(StandardError.builder()
                        .status(status.value())
                        .error("Server Error")
                        .message(List.of(message))
                        .path(request.getRequestURI())
                        .trace(ExceptionUtils.getStackTrace(e))
                        .build());
    }
}
