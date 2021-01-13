package com.antrianservice.repository;

import com.antrianservice.model.History;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends CrudRepository<History, String> {
    List<History> findAll();
}
