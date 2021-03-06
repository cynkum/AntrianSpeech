package com.antrianservice.repository;

import com.antrianservice.model.Pegawai;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PegawaiRepository extends CrudRepository<Pegawai, String> {
    List<Pegawai> findAllByIdCabang(String idCabang);
    List<Pegawai> findAllByNip(String nip);
    List<Pegawai> findAll();
    Pegawai findByUsername(String username);

    @Query(value = "select password from pegawai where username=?1", nativeQuery = true)
    String existsPegawaiByUsername(String username);

    @Query(value = "select hak_akses from pegawai where username=?1", nativeQuery = true)
    String hakAkses(String username);
}
