package com.example.demo.repository;

import com.example.demo.entity.kategori.Kategori;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KategoriRepository extends CrudRepository<Kategori, String> {
    List<Kategori> findAllByIdCabang(String idCabang);
//    @Query(value = "SELECT COUNT(id_kategori) FROM kategori_antrian WHERE id_cabang=?1")
//    Long findJumlahKategoriByIdCabang(String idCabang);
}
