package com.eazyschool.eazyschool.controller;

import com.eazyschool.eazyschool.model.Address;
import com.eazyschool.eazyschool.model.Person;
import com.eazyschool.eazyschool.model.Profile;
import com.eazyschool.eazyschool.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller("profileControllerBean")
public class ProfileController {

    @Autowired
    private PersonRepository personRepository;

    @RequestMapping("displayProfile")
    public ModelAndView displayProfile(Model model, HttpSession session) {
        Profile profile = new Profile();
        ModelAndView modelAndView = new ModelAndView("profile.html");
        modelAndView.addObject("profile",profile);
        Person person = (Person) session.getAttribute("loggedInPerson");


        profile.setName(person.getName());
        profile.setEmail(person.getEmail());
        profile.setMobileNumber(person.getMobileNumber());

        if(null != person.getAddress() && person.getAddress().getAddressId() > 0){
            profile.setCity(person.getAddress().getCity());
            profile.setState(person.getAddress().getState());
            profile.setAddress1(person.getAddress().getAddress1());
            profile.setAddress2(person.getAddress().getAddress2());
            profile.setZipCode(person.getAddress().getZipCode());
        }

        return modelAndView;
    }

    @RequestMapping("/updateProfile")
    public String updateProfile(@Valid @ModelAttribute("profile") Profile profile, Errors errors,HttpSession httpSession){
        if(errors.hasErrors())
            return "profile.html";

        Person person = (Person) httpSession.getAttribute("loggedInPerson");
        person.setName(profile.getName());
        person.setEmail(profile.getEmail());
        person.setMobileNumber(profile.getMobileNumber());

        if(person.getAddress() == null || person.getAddress().getAddressId() <= 0){
            person.setAddress(new Address());
        }

        person.getAddress().setAddress1(profile.getAddress1());
        person.getAddress().setAddress2(profile.getAddress2());
        person.getAddress().setCity(profile.getCity());
        person.getAddress().setState(profile.getState());
        person.getAddress().setZipCode(profile.getZipCode());

        personRepository.save(person);

        httpSession.setAttribute("loggedInPerson",person);
        return "redirect:/displayProfile";
    }
}
