package com.eazyschool.eazyschool.service;

import com.eazyschool.eazyschool.constants.EazySchoolConstants;
import com.eazyschool.eazyschool.model.Contact;
import com.eazyschool.eazyschool.repository.ContactRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import java.util.List;
import java.util.Optional;


@Data
@Slf4j
@Service
@SessionScope
public class ContactService {

    private ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public boolean saveMessageDetails(Contact contact){
        contact.setStatus(EazySchoolConstants.OPEN);
        Contact contactSaved = contactRepository.save(contact);
        return contactSaved.getContactId() > 0;
    }

    public boolean updateMsgStatus(int contactId){
        boolean isUpdated = false;
        Optional<Contact> contact = contactRepository.findById(contactId);
        contact.ifPresent(contact1 -> contact1.setStatus(EazySchoolConstants.CLOSE));
        Contact contactUpdated = contactRepository.save(contact.get());
        if(null != contactUpdated && contactUpdated.getUpdatedBy() != null) isUpdated = true;
        return isUpdated ;
    }

    public Page<Contact> findMsgsWithOpenStatus(int pageNum, String sortField ,String sortDir){
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNum - 1 , pageSize ,
                sortDir.equals("asc")? Sort.by(sortField).ascending():Sort.by(sortField).descending());
        Page<Contact> contactPage = contactRepository.findByStatus(EazySchoolConstants.OPEN,pageable);
        return contactPage;
    }
}
