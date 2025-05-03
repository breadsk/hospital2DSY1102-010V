package com.example.hospital_vm.controller;

import com.example.hospital_vm.model.Paciente;
import com.example.hospital_vm.service.PacienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.time.LocalDateTime;
import java.net.URI;




@RestController
@RequestMapping("/api/v1/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public String saludo() {
        return "Hola Mundo";
    }

    @GetMapping("/listar")
    public List<Paciente> listarPacientes(){
        return pacienteService.findAll();
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<?> getPatientById(@PathVariable Integer id){

        Optional<Paciente> paciente = pacienteService.getPatientById(id);

        if(paciente.isPresent()){
            //Retornar una respuesta exitosa
            return ResponseEntity.ok()
                    .header("mi-encabeza","valor")
                    .body(paciente.get());
        }else{
            //Respuesta de error con cuerpo personalizado
            Map<String,String> errorBody= new HashMap<>();
            errorBody.put("message","No se encontró el paciente con id: " + id);
            errorBody.put("status","404");
            errorBody.put("timestamp",LocalDateTime.now().toString());

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(errorBody);
        }
    }
    
    @PostMapping
    public ResponseEntity<?> save(@RequestBody Paciente paciente){
        try{

            Paciente pacienteGuardado = pacienteService.save(paciente);

            //Uri del nuevo recurso creado 
            URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(pacienteGuardado.getId())
                        .toUri();

            return ResponseEntity
                        .created(location)//Código 201 created
                        .body(pacienteGuardado);
                        

        }catch(DataIntegrityViolationException e){
            Map<String,String> error = new HashMap<>();
            error.put("message","El email ya está registrado");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);//404
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Paciente> update(@PathVariable int id,@RequestBody Paciente paciente){
        try{

            Paciente pac = pacienteService.getPatientById2(id);
            pac.setId(id);
            pac.setRut(paciente.getRut());
            pac.setNombres(paciente.getNombres());
            pac.setApellidos(paciente.getApellidos());
            pac.setFechaNacimiento(paciente.getFechaNacimiento());
            pac.setCorreo(paciente.getCorreo());

            pacienteService.save(paciente);
            return ResponseEntity.ok(paciente);

        }catch(Exception ex){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable int id){
        try{

            pacienteService.delete(id);
            return ResponseEntity.noContent().build();

        }catch(Exception ex){
            return ResponseEntity.notFound().build();
        }
    }
}
