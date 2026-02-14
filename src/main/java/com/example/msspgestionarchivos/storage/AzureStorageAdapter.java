package com.example.msspgestionarchivos.storage;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.example.msspgestionarchivos.exception.StorageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Slf4j
public class AzureStorageAdapter {

    private final BlobContainerClient containerClient;

    public AzureStorageAdapter(
            BlobServiceClient serviceClient,
            @Value("${azure.blob.container-name}")
            String containerName
    ) {

        this.containerClient =
                serviceClient.getBlobContainerClient(containerName);

        if (!containerClient.exists()) {
            containerClient.create();
            log.info("Contenedor Azure creado: {}", containerName);
        }
    }

    public String upload(String filename, MultipartFile file) {

        try {

            BlobClient blobClient =
                    containerClient.getBlobClient(filename);

            blobClient.upload(
                    file.getInputStream(),
                    file.getSize(),
                    true
            );

            blobClient.setHttpHeaders(
                    new BlobHttpHeaders()
                            .setContentType(file.getContentType())
            );

            log.info("Archivo subido a Azure: {}", filename);

            return blobClient.getBlobUrl();

        } catch (Exception e) {

            log.error("Error subiendo archivo a Azure", e);

            throw new StorageException(
                    "Error subiendo archivo"
            );
        }
    }
}