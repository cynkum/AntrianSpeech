package com.antrianservice.repository;

import com.antrianservice.model.Pegawai;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PegawaiRepository extends CrudRepository<Pegawai, String> {
    List<Pegawai> findAllByIdCabang(String idCabang);
    List<Pegawai> findAllByNip(String nip);
    List<Pegawai> findAll();
    Pegawai findByUsername(String username);
    Pegawai findByPassword(String password);
    Pegawai findByNip(String nip);
//    List<Pegawai> findPassword(String password);

}
