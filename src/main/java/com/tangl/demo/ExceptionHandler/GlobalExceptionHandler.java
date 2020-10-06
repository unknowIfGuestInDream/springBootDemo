package com.tangl.demo.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.*;

/**
 * @author: TangLiang
 * @date: 2020/9/2 9:14
 * @since: 1.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @SuppressWarnings("unchecked")
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map handle(ValidationException exception) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();
        if (exception instanceof ConstraintViolationException) {
            ConstraintViolationException exs = (ConstraintViolationException) exception;

            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
            for (ConstraintViolation<?> item : violations) {
                list.add(new HashMap() {
                    {
                        put("message", item.getMessage());
                    }
                });
            }
            //打印验证不通过的信息
            log.error("{}", exception.getLocalizedMessage());
        }
        result.put("result", list);
        result.put("success", false);
        return result;
    }
}
