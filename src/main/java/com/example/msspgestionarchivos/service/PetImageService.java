package com.example.msspgestionarchivos.service;

import com.example.msspgestionarchivos.dto.PetImageResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PetImageService {

    List<PetImageResponseDTO> uploadMultiple(
            List<MultipartFile> files,
            String petCode
    );

    List<PetImageResponseDTO>
    getByPet(String petCode);
}