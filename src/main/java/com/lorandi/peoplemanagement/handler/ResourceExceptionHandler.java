package com.lorandi.peoplemanagement.handler;

import com.lorandi.peoplemanagement.helper.MessageHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.ValidationException;

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
}
