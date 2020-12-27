package com.example.demo.repository;

import com.example.demo.entity.History;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends CrudRepository<History, String> {

}
