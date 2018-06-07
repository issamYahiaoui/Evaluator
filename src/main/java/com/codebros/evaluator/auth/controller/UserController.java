package com.codebros.evaluator.auth.controller;


import com.codebros.evaluator.auth.model.User;
import com.codebros.evaluator.auth.service.UserService;
import com.codebros.evaluator.workspace.model.*;
import com.codebros.evaluator.workspace.repository.ApplicantRepository;
import com.codebros.evaluator.workspace.service.FolderService;
import org.apache.catalina.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@Controller
public class UserController {



    @Autowired
    private UserService userService;

    @Autowired
    private ApplicantRepository applicantService;

    @Autowired
    private FolderService folderService;


    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("auth/login");
        return modelAndView;
    }






    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("auth/login");
        } else {
            Applicant applicant  =  new Applicant() ;
            applicant.setUser(user);
            Folder folder = new Folder() ;
            folderService.init(folder);
            folderService.save(folder) ;
            applicant.setFolder(folder);
            Application application = new Application() ;
            application.setEtablissement(new Etablissement());
            application.setSpeciality(new Speciality());
            applicant.setAppliaction(application);
            userService.saveUser(user);
            applicantService.save(applicant) ;
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("auth/login");

        }
        return modelAndView;
    }

    @RequestMapping(value="/home", method = RequestMethod.GET)
    public ModelAndView home(){

        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getName()  + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        /***** Check user role ****/
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ADMIN"));
        boolean isApplicant = auth.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("APPLICANT"));
        boolean isCommission = auth.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("COMMISSION"));
        /****** *****/
        String view = "" ;
        if(isAdmin) {

            view = "workspace/admin/index" ;
        }
        if(isApplicant) {
            view = "workspace/applicant/stats";
        }
        if(isCommission) {
            view = "workspace/admin/index" ;
        }
        modelAndView.setViewName(view);
        return modelAndView;
    }


}