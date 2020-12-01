package com.example.demo.repository;

import com.example.demo.entity.ErrorDictionary;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorMessageRepository extends PagingAndSortingRepository<ErrorDictionary,String> {
}
