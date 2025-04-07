package com.eazyschool.eazyschool.rest;

import com.eazyschool.eazyschool.constants.EazySchoolConstants;
import com.eazyschool.eazyschool.model.Contact;
import com.eazyschool.eazyschool.model.Response;
import com.eazyschool.eazyschool.repository.ContactRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@CrossOrigin(origins="*")
@RequestMapping(value = "/api/contact" , produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
public class ContactRestController {

    @Autowired
    private ContactRepository contactRepository;

    @GetMapping("/getMessagesByStatus")
    public List<Contact>  getMessagesByStatus( @RequestParam(name = "status") String status) {
        return contactRepository.findByStatus(status);
    }

    @GetMapping("getAllMessagesByStatus")
    public List<Contact> getAllMessagesByStatus( @RequestBody Contact contact) {
        if(null != contact && null != contact.getStatus()){
            return contactRepository.findByStatus(contact.getStatus());
        }
        return List.of();
    }

    @PostMapping("saveMsg")
    public ResponseEntity<Response> saveMsg(@RequestHeader("invocationFrom") String invocationFrom,
                                            @Valid @RequestBody Contact contact){
        log.info(String.format("Header Invocation from %s",invocationFrom));
        contactRepository.save(contact);
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Message Saved Successfully");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("isMsgSaved","true")
                .body(response);
    }

    @DeleteMapping("deleteMsg")
    public ResponseEntity<Response> deleteMsg(RequestEntity<Contact> requestEntity){
        HttpHeaders headers = requestEntity.getHeaders();
        headers.forEach((key, value) ->
                log.info(String.format("Header Key %s, Value %s",key,value.stream().collect(
                        Collectors.joining("|"))))
        );

        Contact contact = requestEntity.getBody();
        contactRepository.deleteById(contact.getContactId());

        log.info("Contact Recieved Was : " + contact);

        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Message Successfully Deleted");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PatchMapping("/closeMsg")
    public ResponseEntity<Response> closeMsg(@RequestBody Contact contactReq){
        Response response = new Response();
        Optional<Contact> contactOptional = contactRepository.findById(contactReq.getContactId());
        if(contactOptional.isPresent()){
            contactOptional.get().setStatus(EazySchoolConstants.CLOSE);
            contactRepository.save(contactOptional.get());
        }else {
            response.setStatusCode("400");
            response.setStatusMsg("Invalid Id for the Contact Was provided");

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }

        response.setStatusMsg("Message successfully Closed !");
        response.setStatusCode("200");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

}

