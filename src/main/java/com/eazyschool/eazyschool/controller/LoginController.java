package com.eazyschool.eazyschool.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class LoginController {
    @RequestMapping(value = "/login" , method = {RequestMethod.GET,RequestMethod.POST})
    public String displayLoginPage(@RequestParam(value = "error", required = false) String error,
                                   @RequestParam(value = "logout", required = false) String logout,
                                   @RequestParam(value = "register" , required = false) String register, Model model) {
        String errorMessage = null;
        if(error !=null)
            errorMessage = "Username or Password is incorrect";
        else if(logout != null)
            errorMessage = "You have been logged out successfully.";
        else if(register != null)
            errorMessage = "You have registered successfully please login . ";
        model.addAttribute("errorMessage", errorMessage);
        return "login.html";

    }

    @GetMapping("/logout")
    public String logoutController(HttpServletRequest request, HttpServletResponse response,Authentication auth){
        /*
            Both Parameter Injection of Authentication Object will work as well as the below method the in Parameter one
            Spring Security will automatically Inject the details in the Auth Object where as in the below one we will do
            it by ourselves
            1. Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            2, Parameter Injection of the authentication object
         */
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }
}
