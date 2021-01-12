package com.antrianservice.repository;

import com.antrianservice.model.Cabang;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CabangRepository extends CrudRepository<Cabang, String> {
   List<Cabang> findAll();
   @Query(value = "SELECT COUNT(*) max FROM cabang", nativeQuery = true)
   Long countCabang();
}
