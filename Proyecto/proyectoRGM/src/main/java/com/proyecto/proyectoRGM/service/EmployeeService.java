package com.proyecto.proyectoRGM.service;

import com.proyecto.proyectoRGM.entities.Employee;
import com.proyecto.proyectoRGM.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {


    //Autowired recoge todos los métodos de EmployeeRepository
    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeService(){
    }

    //Todos los siguientes métodos los recogerá EmployeeController

    public List<Employee> getEmploymentHistory(){
        List<Employee> lista = employeeRepository.findAll();

        for(Employee e: lista){
            if(e.getEstadoCivil().equals("S")){
                e.setEstadoCivil("Soltero");
            }else if(e.getEstadoCivil().equals("C")){
                e.setEstadoCivil("Casado");
            }

            if(e.getServMilitar().equals("S")){
                e.setServMilitar("Sí");
            }else if(e.getServMilitar().equals("N")){
                e.setServMilitar("No");
            }

        }

        return lista;
    }

    public ResponseEntity<List<Employee>> getEmployees(){

        List<Employee> lista = employeeRepository.findAll();
        List<Employee> listaModificada = new ArrayList<>();

        for(Employee e: lista){
            if(e.getEstadoCivil().equals("S")){
                e.setEstadoCivil("Soltero");
            }else if(e.getEstadoCivil().equals("C")){
                e.setEstadoCivil("Casado");
            }

            if(e.getServMilitar().equals("S")){
                e.setServMilitar("Sí");
            }else if(e.getServMilitar().equals("N")){
                e.setServMilitar("No");
            }

            if(e.getFechaBaja()==null){
                listaModificada.add(e);
            }
        }

        try{
            return new ResponseEntity<>(listaModificada, HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(listaModificada, HttpStatus.BAD_REQUEST);
        }

    }
    public ResponseEntity<String> insertEmpleado(@RequestBody Employee empleado){

        //Validación de Edad
        int years = 0;

        try{
            LocalDate birth = empleado.getfNacimiento().toLocalDate();
            LocalDate now = LocalDate.now();
            years = Period.between(birth, now).getYears();
            if(years < 18){
                throw new IllegalArgumentException("La diferencia es menor a 18 años: " + years);
            }else if(years > 67){
                throw new IllegalArgumentException("La diferencia es mayor de 67 años: " + years);
            }
            else{
                Employee employeeSave = employeeRepository.save(empleado);
                return new ResponseEntity<>(employeeSave.toString(), HttpStatus.OK);
            }
        }catch(IllegalArgumentException e){
                return new ResponseEntity<>(e.getMessage() , HttpStatus.EXPECTATION_FAILED);

        }catch(Exception e2){
            return new ResponseEntity<>("El empleado no se ha podido insertar: (NIF " + empleado.getNif() + " ya registrado)",
                    HttpStatus.EXPECTATION_FAILED);
        }


    }


    public void deleteEmpleadoPorId(int id){
        employeeRepository.deleteById(id);
    }

    public void deleteEmpleado(Employee empleado){
        employeeRepository.delete(empleado);
    }

    //EXCEPCIÓN FECHA DATA INTEGRITY

    //@Transactional
    public Employee editFechaBaja(String nif){
        //employeeRepository.updatefechaBajaByNif(empleadoUpdate.getNif(), empleadoUpdate.getDate());

        Employee employee = employeeRepository.findByNif(nif);

        if(employee!=null){
            employee.setFechaBaja(Date.valueOf(LocalDate.now()));
        }
        return employeeRepository.save(employee);
    }

}
