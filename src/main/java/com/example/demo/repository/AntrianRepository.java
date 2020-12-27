package com.example.demo.repository;

import android.util.Log;
import com.example.demo.entity.Antrian;
import com.example.demo.entity.Pegawai;
import org.hibernate.type.Type;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import android.util.*;
@Repository
public interface AntrianRepository extends CrudRepository<Antrian, Integer> {
    List<Antrian> findAllByIdKategori(String idKategori);
    @Query(value = "SELECT COUNT(*) max FROM antrian WHERE DATE(tanggal_antri) = CURDATE() AND id_kategori =?1", nativeQuery = true)
    Long findAllByNomorAntrian(String id_kategori);
    List<Antrian> findByNamaNasabahAndTanggalAntri(String namaNasabah, Date tanggalAntri);
//    List<Antrian> findAllByNomorAntrian();
    //Antrian existByIdKategori(String idKategori);
}
