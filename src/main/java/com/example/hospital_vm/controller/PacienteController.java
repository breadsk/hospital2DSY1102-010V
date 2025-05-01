package com.example.hospital_vm.controller;

import com.example.hospital_vm.model.Paciente;
import com.example.hospital_vm.service.PacienteService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.RequestParam;



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
    
    

}
