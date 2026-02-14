package com.example.msspgestionarchivos.dto;

import com.example.msspgestionarchivos.entity.PetImage;

import java.time.LocalDateTime;

public record PetImageResponseDTO(

        Long id,
        String petCode,
        String imageUrl,
        String fileName,
        LocalDateTime createdAt
) {

    public static PetImageResponseDTO fromEntity(
            PetImage entity
    ) {
        return new PetImageResponseDTO(
                entity.getId(),
                entity.getPetCode(),
                entity.getImageUrl(),
                entity.getFileName(),
                entity.getCreatedAt()
        );
    }
}