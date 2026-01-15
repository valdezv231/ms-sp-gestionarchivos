package com.example.msspgestionarchivos.controller;

import com.example.msspgestionarchivos.dto.ArchivoResponse;
import com.example.msspgestionarchivos.service.ArchivoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/carga-archivos")
public class ArchivoController {

    private final ArchivoService service;

    public ArchivoController(ArchivoService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ArchivoResponse subir(
            @RequestParam("archivo") MultipartFile archivo,
            @RequestParam("codigoAnimal") String codigoAnimal
    ) {
        return service.subirFoto(archivo, codigoAnimal);
    }
}
