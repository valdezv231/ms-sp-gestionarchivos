package com.example.msspgestionarchivos.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="applications")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "application_name")
    private String applicationName;

    @Column(name = "application_code")
    private String applicationCode;

    @Column(name = "consumer_id")
    private String consumerId;
}
