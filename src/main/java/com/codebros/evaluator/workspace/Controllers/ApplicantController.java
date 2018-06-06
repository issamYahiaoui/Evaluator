package com.codebros.evaluator.workspace.Controllers;


import com.codebros.evaluator.auth.model.User;
import com.codebros.evaluator.auth.service.UserService;
import com.codebros.evaluator.workspace.model.*;
import com.codebros.evaluator.workspace.repository.*;
import com.codebros.evaluator.workspace.service.FolderService;
import com.codebros.evaluator.workspace.service.FolderServiceImpl;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping("/applicant")
public class ApplicantController {



     @Autowired
     private ApplicationRepository applicationRepository ;
     @Autowired
     private  ApplicantRepository applicantRepository ;

     @Autowired
     private EtablissementRepository etablissementRepository ;
     @Autowired
     private SpecialityRepository specialityRepository ;
    @Autowired
    private FolderRepository folderRepository ;
    @Autowired
    private RequirementRepository requirementRepository ;

    @Autowired
    private UserService userService ;

    @Autowired
    FolderServiceImpl folderService ;

    @GetMapping("/")
    public String index() {
        return "workspace/applicant/index";
    }

    @GetMapping("/folder")
    public ModelAndView folder() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Applicant applicant = applicantRepository.findByUser(user) ;
        Folder folder =  applicant.getFolder();
        modelAndView.addObject("folder",folder);
        modelAndView.addObject("administratif_requirements",folder.getRequirements("ADMINISTRATIF"));
        modelAndView.addObject("scientific_requirements",folder.getRequirements("SCIENTIFIC"));
        modelAndView.addObject("other_requirements",folder.getRequirements("AUTRE"));

        modelAndView.setViewName("workspace/applicant/folder");
        return modelAndView;
    }



    /*** applicant form  ****/

    @GetMapping("/applicant-form")
    public ModelAndView applicantForm (){
        ModelAndView modelAndView = new ModelAndView();
        Application application =  new Application() ;
        modelAndView.addObject("application", application);
        modelAndView.addObject("etablissement", etablissementRepository.findAll());
        modelAndView.addObject("speciality", specialityRepository.findAll());
        modelAndView.setViewName("workspace/applicant/applicant-form");
        return modelAndView;

    }

    @PostMapping("/applicant-form")
    public ModelAndView applicationFormPost (@Valid Application application) {
        ModelAndView modelAndView = new ModelAndView();
        applicationRepository.save(application);
        modelAndView.addObject("application", application);
        modelAndView.addObject("successMessage", "Opération terminée avec succes");
        modelAndView.setViewName("workspace/applicant/applicant-form");
        return  modelAndView ;
    }





    /*-**************-*/

    @GetMapping("/evaluation")
    public String evaluation (){return "workspace/applicant/evaluation" ; }


    @GetMapping("/stats")
    public String stats(){return "workspace/applicant/stats";}

}
