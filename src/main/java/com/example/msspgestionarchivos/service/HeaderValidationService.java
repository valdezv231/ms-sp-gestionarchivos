package com.example.msspgestionarchivos.service;

import com.example.msspgestionarchivos.repository.ApplicationClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HeaderValidationService {

    private final ApplicationClientRepository repository;

    public HeaderValidationService(ApplicationClientRepository repository) {
        this.repository = repository;
    }

    public void validate(
            String applicationName,
            String applicationCode,
            String consumerId
    ) {

        log.info("Validando headers contra BD...");

        repository
                .findByApplicationNameAndApplicationCodeAndConsumerId(
                        applicationName,
                        applicationCode,
                        consumerId
                )
                .orElseThrow(() -> {
                    log.error("Aplicación no autorizada");
                    return new RuntimeException("Aplicación no autorizada");
                });

        log.info("Validación exitosa");
    }
}
