package com.example.demo.repository;

import com.example.demo.entity.cabang.Cabang;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CabangRepository extends CrudRepository<Cabang, String> {
   //List<Cabang> findAllByIdCabang(String idCabang);
}
