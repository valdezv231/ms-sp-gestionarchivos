package com.example.msspgestionarchivos.repository;

import com.example.msspgestionarchivos.entity.ApplicationClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationClientRepository extends JpaRepository<ApplicationClient, Long> {

    Optional<ApplicationClient> findByApplicationNameAndApplicationCodeAndConsumerId(
            String applicationName,
            String applicationCode,
            String consumerId
    );
}
