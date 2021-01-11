package com.antrianservice.repository;

import com.antrianservice.model.Antrian;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AntrianRepository extends CrudRepository<Antrian, Integer> {
    List<Antrian> findAll();
    List<Antrian> findAllByIdKategori(String idKategori);
    @Query(value = "SELECT COUNT(*) max FROM antrian WHERE DATE(tanggal_antri) = CURDATE() AND id_kategori =?1", nativeQuery = true)
    Long countAntrianByIdKategori(String id_kategori);


}
