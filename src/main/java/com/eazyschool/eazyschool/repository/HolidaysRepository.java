package com.eazyschool.eazyschool.repository;


import com.eazyschool.eazyschool.model.Holiday;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HolidaysRepository extends CrudRepository<Holiday,String> {

}
