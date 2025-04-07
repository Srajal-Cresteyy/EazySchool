package com.eazyschool.eazyschool.repository;

import com.eazyschool.eazyschool.model.Contact;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface ContactRepository extends CrudRepository<Contact,Integer> {
    List<Contact> findByStatus(String status);

    @Query("Select c from Contact c WHERE c.status = :status")
    Page<Contact> findByStatusByQueries(String status, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Contact c SET c.status = :status where c.contactId = :contactId")
    int updataStatusById(String status , int contactId);

    @Modifying
    @Transactional
    int updateMsgStatus(String status,int contactId);

    Page<Contact> findOpenMsgs(String status,Pageable pageable);

}
