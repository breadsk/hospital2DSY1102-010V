package com.example.hospital_vm.service;

import com.example.hospital_vm.model.Atencion;
import com.example.hospital_vm.repository.AtencionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AtencionService {

    @Autowired
    private AtencionRepository atencionRepository;

    public List<Atencion> findAll(){
        return atencionRepository.findAll();
    }

    public Optional<Atencion> getAtentionById(int id){
        return atencionRepository.findById(id);
    }

    public Atencion save(Atencion atencion){
        return atencionRepository.save(atencion);
    }

    public void delete(int id){
        atencionRepository.deleteById(id);
    }

}
