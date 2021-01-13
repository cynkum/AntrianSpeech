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
    @Query(value = "SELECT COUNT(*) max FROM antrian WHERE DATE(tanggal_antri) = CURDATE() AND id_kategori =?1", nativeQuery = true)
    Long countAntrianByIdKategori(String idkategori);
    @Query(value = "SELECT * FROM antrian WHERE DATE(tanggal_antri) = CURDATE() AND id_kategori =?1", nativeQuery = true)
    List<Antrian> findAllByIdKategori(String idKategori);
    @Query(value = "select * from antrian where status_antrian=?2 AND id_kategori=?1 AND DATE(tanggal_antri)=CURDATE() ORDER BY nomor_antrian", nativeQuery = true)
    List<Antrian> findAntrianByTanggalAntri(String idKategori, String statusAntrian);
    @Query(value = "select count(*) from antrian where id_kategori=?1", nativeQuery = true)
    Long countKategori(String idKategori);

}
