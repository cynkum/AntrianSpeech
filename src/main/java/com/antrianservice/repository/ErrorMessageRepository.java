package com.antrianservice.repository;

import com.antrianservice.response.ErrorDictionary;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorMessageRepository extends PagingAndSortingRepository<ErrorDictionary,String> {
}
