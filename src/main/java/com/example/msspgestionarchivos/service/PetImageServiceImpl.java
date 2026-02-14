package com.example.msspgestionarchivos.service;

import com.example.msspgestionarchivos.dto.PetImageResponseDTO;
import com.example.msspgestionarchivos.entity.PetImage;
import com.example.msspgestionarchivos.exception.InvalidFileTypeException;
import com.example.msspgestionarchivos.repository.PetImageRepository;
import com.example.msspgestionarchivos.storage.AzureStorageAdapter;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class PetImageServiceImpl implements PetImageService {

    private static final Set<String>
            EXT_PERMITIDAS =
            Set.of(
                    ".jpg",
                    ".jpeg",
                    ".png",
                    ".gif",
                    ".webp"
            );

    private final AzureStorageAdapter storage;
    private final PetImageRepository repository;

    public PetImageServiceImpl(
            AzureStorageAdapter storage,
            PetImageRepository repository
    ) {
        this.storage = storage;
        this.repository = repository;
    }

    @Override
    public List<PetImageResponseDTO>uploadMultiple(
            List<MultipartFile> files,
            String petCode
    ) {

        log.info(
                "Subiendo {} imágenes para mascota {}",
                files.size(),
                petCode
        );

        return files.stream()
                .map(file -> uploadSingle(
                        file,
                        petCode
                ))
                .map(
                        PetImageResponseDTO
                                ::fromEntity
                )
                .toList();
    }

    private PetImage uploadSingle(MultipartFile file,String petCode) {
        validarArchivo(file);
        String extension =
                obtenerExtension(file);
        String filename =
                petCode + "_"
                        + UUID.randomUUID()
                        + extension;
        String url =
                storage.upload(
                        filename,
                        file
                );
        PetImage entity =
                PetImage.builder()
                        .petCode(petCode)
                        .imageUrl(url)
                        .fileName(filename)
                        .build();
        return repository.save(entity);
    }

    @Override
    public List<PetImageResponseDTO>getByPet(String petCode) {
        log.info(
                "Consultando imágenes de {}",
                petCode
        );
        return repository
                .findByPetCode(petCode)
                .stream()
                .map(
                        PetImageResponseDTO
                                ::fromEntity
                )
                .toList();
    }

    private void validarArchivo(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new InvalidFileTypeException(
                    "Archivo vacío"
            );
        }

        String ext =
                obtenerExtension(file);

        if (!EXT_PERMITIDAS
                .contains(ext)) {

            throw new InvalidFileTypeException(
                    "Tipo no permitido: "
                            + ext
            );
        }
    }

    private String obtenerExtension(MultipartFile file) {
        String nombre =
                file.getOriginalFilename();

        if (nombre == null
                || !nombre.contains(".")) {

            throw new InvalidFileTypeException(
                    "Archivo sin extensión"
            );
        }

        return nombre
                .substring(
                        nombre.lastIndexOf(".")
                )
                .toLowerCase();
    }
}