package com.example.hospital_vm.repository;

import com.example.hospital_vm.model.Atencion;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;



public interface AtencionRepository  extends JpaRepository<Atencion,Integer>{

}
