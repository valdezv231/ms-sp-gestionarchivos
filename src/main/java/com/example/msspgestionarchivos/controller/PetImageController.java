package com.example.msspgestionarchivos.controller;

import com.example.msspgestionarchivos.dto.PetImageResponseDTO;
import com.example.msspgestionarchivos.service.PetImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/images")
@Slf4j
public class PetImageController {

    private final PetImageService service;

    public PetImageController(PetImageService service) {
        this.service = service;
    }

    @PostMapping
    public List<PetImageResponseDTO>upload(@RequestParam("files")
            List<MultipartFile> files,
            @RequestParam
            String petCode
    ) {
        log.info(
                "Request upload para {}",
                petCode
        );
        return service
                .uploadMultiple(
                        files,
                        petCode
                );
    }

    @GetMapping("/{petCode}")
    public List<PetImageResponseDTO>getByPet(@PathVariable String petCode) {
        return service
                .getByPet(petCode);
    }
}