package com.example.msspgestionarchivos.interceptor;

import com.example.msspgestionarchivos.exception.MissingHeaderException;
import com.example.msspgestionarchivos.service.HeaderValidationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class HeaderValidationInterceptor implements HandlerInterceptor {

    private final HeaderValidationService validationService;

    public HeaderValidationInterceptor(
            HeaderValidationService validationService
    ) {
        this.validationService = validationService;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {

        Map<String, String> headers = new HashMap<>();

        headers.put("Id-Transaccion",
                request.getHeader("Id-Transaccion"));
        headers.put("Application-Name",
                request.getHeader("Application-Name"));
        headers.put("Application-Code",
                request.getHeader("Application-Code"));
        headers.put("Consumer-Id",
                request.getHeader("Consumer-Id"));

        List<String> missingHeaders = headers.entrySet()
                .stream()
                .filter(e ->
                        e.getValue() == null ||
                                e.getValue().isBlank()
                )
                .map(Map.Entry::getKey)
                .toList();

        if (!missingHeaders.isEmpty()) {

            String message =
                    "Headers faltantes: " +
                            String.join(", ", missingHeaders);

            log.error(message);

            throw new MissingHeaderException(message);
        }

        validationService.validate(
                headers.get("Application-Name"),
                headers.get("Application-Code"),
                headers.get("Consumer-Id")
        );

        return true;
    }
}
