package com.proyecto.proyectoRGM.repositories;

import com.proyecto.proyectoRGM.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    Project findById(int id);
}
