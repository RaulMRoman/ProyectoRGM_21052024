package com.proyecto.proyectoRGM.service;

import com.proyecto.proyectoRGM.entities.Project;
import com.proyecto.proyectoRGM.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectService(){
    }

    public List<Project> getProyectos(){

        List<Project> lista = projectRepository.findAll();
        List<Project> listaModificada = new ArrayList<>();

        for(Project e: lista) {

            if (e.getFechaBaja() == null) {
                listaModificada.add(e);
            }
        }
        return listaModificada;
    }

    public ResponseEntity<Project> insertProyecto(@RequestBody Project proyecto){
        return new ResponseEntity<Project>(projectRepository.save(proyecto), HttpStatus.OK);
    }

    public Project deleteProyectoPorId(int id){
        Project project = projectRepository.findById(id);

        if(project!=null){
            project.setFechaBaja(LocalDate.now());
        }

        return projectRepository.save(project);
    }
}
