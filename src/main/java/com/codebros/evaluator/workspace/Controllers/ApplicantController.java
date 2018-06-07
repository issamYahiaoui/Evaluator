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
        String url = "/uploadForm" ;
        modelAndView.addObject("folder",folder);
        modelAndView.addObject("url",url);
        modelAndView.addObject("administratif_requirements",folder.getRequirements("ADMINISTRATIF"));
        modelAndView.addObject("scientific_requirements",folder.getRequirements("SCIENTIFIC"));
        modelAndView.addObject("other_requirements",folder.getRequirements("AUTRE"));

        modelAndView.setViewName("workspace/applicant/folder");
        return modelAndView;
    }



    /*** applicant form  ****/

    @GetMapping("/applicant-form")
    public ModelAndView applicantForm (

    ){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Applicant applicant = applicantRepository.findByUser(user) ;
        Application application = applicant.getAppliaction() ;
        modelAndView.addObject("application", application);
        modelAndView.addObject("applicant", applicant);
        modelAndView.addObject("etablissement", etablissementRepository.findAll());
        modelAndView.addObject("speciality", specialityRepository.findAll());
        modelAndView.setViewName("workspace/applicant/applicant-form");
        return modelAndView;

    }

    @PostMapping("/applicant-form")
    public ModelAndView applicationFormPost (@Valid Application application ,
                                             @RequestParam("etablissement_id") Integer etablissement_id ,
                                             @RequestParam("speciality_id") Integer specility_id
                                             ) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Applicant applicant = applicantRepository.findByUser(user) ;
        Etablissement etablissement = etablissementRepository.findById(etablissement_id) ;
        Speciality speciality = specialityRepository.findById(specility_id);
        Application new_application = applicant.getAppliaction();
        new_application.setSpeciality(speciality);
        new_application.setEtablissement(etablissement);
        new_application.setAttestation_equivalence(application.getAttestation_equivalence());
        new_application.setDate_displome_mca(application.getDate_displome_mca());
        new_application.setDate_doctorat_etat(application.getDate_doctorat_etat());
        new_application.setDoctorat_habilitation_universitaire(application.getDoctorat_habilitation_universitaire());
        new_application.setFirstName(application.getFirstName());
        new_application.setName(application.getName());
        new_application.setPhone(application.getPhone());
        applicationRepository.save(new_application);
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
