package com.example.msspgestionarchivos.infrastructure.storage;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.example.msspgestionarchivos.exception.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class AzureBlobStorageAdapter {

    private final BlobContainerClient containerClient;

    public AzureBlobStorageAdapter(
            BlobServiceClient serviceClient,
            @Value("${azure.blob.container-name}") String containerName
    ) {
        this.containerClient = serviceClient.getBlobContainerClient(containerName);
    }

    public String subir(String nombreArchivo, MultipartFile archivo) {

        try {
            BlobClient blobClient = containerClient.getBlobClient(nombreArchivo);

            blobClient.upload(
                    archivo.getInputStream(),
                    archivo.getSize(),
                    true
            );

            BlobHttpHeaders headers = new BlobHttpHeaders()
                    .setContentType(archivo.getContentType());
            blobClient.setHttpHeaders(headers);

            return blobClient.getBlobUrl();

        } catch (Exception e) {
            throw new FileUploadException("Error al subir archivo a Azure");
        }
    }
}

