package com.example.msspgestionarchivos.service;

import com.example.msspgestionarchivos.dto.ArchivoResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ArchivoService {

    ArchivoResponse subirFoto(
            MultipartFile archivo,
            String codigoAnimal
    );
}

