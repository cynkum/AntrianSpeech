package com.antrianservice.repository;

import com.antrianservice.model.Kategori;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KategoriRepository extends CrudRepository<Kategori, String> {
    List<Kategori> findAll();
    List<Kategori> findAllByIdCabang(String idCabang);
    List<Kategori> findByIdCabangAndAndKodeKategori(String idCabang, String kodeKategori);
    @Query(countQuery = "SELECT COUNT(id_kategori) FROM kategori_antrian WHERE id_cabang = :id_cabang", nativeQuery = true)
    Long countKategoriByIdCabang(String idCabang);

}
