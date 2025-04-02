package com.eazyschool.eazyschool.repository;

import com.eazyschool.eazyschool.model.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface ContactRepository extends CrudRepository<Contact,Integer> {
    List<Contact> findByStatus(String status);
}
