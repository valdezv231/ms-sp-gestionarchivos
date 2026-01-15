package com.example.msspgestionarchivos.service.impl;

import com.example.msspgestionarchivos.dto.ArchivoResponse;
import com.example.msspgestionarchivos.exception.FileUploadException;
import com.example.msspgestionarchivos.infrastructure.storage.AzureBlobStorageAdapter;
import com.example.msspgestionarchivos.service.ArchivoService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ArchivoServiceImpl implements ArchivoService {

    private final AzureBlobStorageAdapter storage;

    public ArchivoServiceImpl(AzureBlobStorageAdapter storage) {
        this.storage = storage;
    }

    @Override
    public ArchivoResponse subirFoto(
            MultipartFile archivo,
            String codigoAnimal
    ) {

        validarArchivo(archivo);

        String extension = obtenerExtension(archivo);
        String nombreArchivo = codigoAnimal + extension;

        String url = storage.subir(
                nombreArchivo,
                archivo
        );

        return new ArchivoResponse(nombreArchivo, url);
    }

    private void validarArchivo(MultipartFile archivo) {
        if (archivo == null || archivo.isEmpty()) {
            throw new FileUploadException("El archivo es obligatorio");
        }
    }

    private String obtenerExtension(MultipartFile archivo) {
        String nombre = archivo.getOriginalFilename();

        if (nombre == null || !nombre.contains(".")) {
            throw new FileUploadException("Archivo sin extensión válida");
        }

        return nombre.substring(nombre.lastIndexOf("."));
    }
}
