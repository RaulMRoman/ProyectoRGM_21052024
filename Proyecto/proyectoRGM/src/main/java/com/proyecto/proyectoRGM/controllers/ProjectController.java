package com.proyecto.proyectoRGM.controllers;

import com.proyecto.proyectoRGM.entities.Project;
import com.proyecto.proyectoRGM.service.ProjectService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public List<Project> getProyectos(){
        return projectService.getProyectos();
    }

    @PostMapping("/insert")
    public ResponseEntity<Project> insertProyecto(@RequestBody Project proyecto){
        return projectService.insertProyecto(proyecto);
    }

    @DeleteMapping(path="/{id}")
    public void deleteEmpleadoPorId(@PathVariable int id){
        projectService.deleteProyectoPorId(id);
    }

    @Transactional
    @PutMapping(path="/update/{id}")
    public void editFechaBaja(@PathVariable int id){
        projectService.deleteProyectoPorId(id);
    }
}
