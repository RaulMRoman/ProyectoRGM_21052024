package com.proyecto.proyectoRGM.repositories;

import com.proyecto.proyectoRGM.entities.EmployeeProject;
import com.proyecto.proyectoRGM.entities.EmployeeProjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface EmployeeProjectRepository extends JpaRepository<EmployeeProject, EmployeeProjectId> {

    @Modifying
    @Query("UPDATE Employee e SET e.fechaBaja = :fechaBaja WHERE e.nif LIKE :nif")
    void updatefechaBajaByNif(@Param("nif") String nif, @Param("fechaBaja") Date fechaBaja);
}
