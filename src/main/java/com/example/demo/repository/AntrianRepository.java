package com.example.demo.repository;

import com.example.demo.entity.Antrian;
import com.example.demo.entity.Pegawai;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AntrianRepository extends CrudRepository<Antrian, Integer> {
    List<Antrian> findAllByIdKategori(String idKategori);
    //Antrian existByIdKategori(String idKategori);
}
