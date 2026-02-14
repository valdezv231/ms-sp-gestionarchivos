package com.example.msspgestionarchivos.repository;

import com.example.msspgestionarchivos.entity.PetImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetImageRepository extends JpaRepository<PetImage, Long> {
    List<PetImage> findByPetCode(String petCode);
}
