package com.proyecto.proyectoRGM.controllers;

import com.proyecto.proyectoRGM.entities.Employee;
import com.proyecto.proyectoRGM.service.EmployeeService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@Transactional
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/empleados")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(path="/history")
    public List<Employee> getEmploymentHistory(){
        return employeeService.getEmploymentHistory();
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getEmployees(){
        return employeeService.getEmployees();
    }

    @PostMapping(path="/insert")
    public ResponseEntity<String> insertEmpleado(
            @RequestBody @Valid Employee empleado){
        return employeeService.insertEmpleado(empleado);
    }

    @DeleteMapping(path="/{id}")
    public void deleteEmpleadoPorId(@PathVariable @Valid int id){
        employeeService.deleteEmpleadoPorId(id);
    }

    /*@Transactional
    //@PutMapping(path="/update?nif={nif}/date={date}")
    @PutMapping(path="/update")
    public void updateFechaBajaByNif(@PathVariable String nif, @PathVariable Date date){
        employeeService.updateFechaBajaByNif(nif, date);
    }*/

    @Transactional
    @PutMapping(path="/update/{nif}")
    public ResponseEntity<String> editFechaBaja(@PathVariable @Valid String nif){
        return employeeService.editFechaBaja(nif);
    }
}
