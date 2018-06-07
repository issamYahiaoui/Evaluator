package com.codebros.evaluator.workspace.Controllers;


import com.codebros.evaluator.auth.model.User;
import com.codebros.evaluator.workspace.model.Applicant;
import com.codebros.evaluator.workspace.model.Folder;
import com.codebros.evaluator.workspace.model.Requirement;
import com.codebros.evaluator.workspace.repository.ApplicantRepository;
import com.codebros.evaluator.workspace.repository.FolderRepository;
import com.codebros.evaluator.workspace.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Controller
@RequestMapping("/commission")
public class CommissionController {

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private FolderRepository folderRepository;

    @GetMapping("/applicant-list")
    public ModelAndView applicantList (){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        modelAndView.addObject("applicants",applicantRepository.findAll());

        modelAndView.setViewName("workspace/commission/applicant-list");
        return modelAndView;

    }

    @GetMapping("/show-folder/{applicant_id}")
    public ModelAndView showFolder(
            @PathVariable("applicant_id") Integer applicant_id

    ){
        ModelAndView modelAndView = new ModelAndView();
        Applicant applicant = applicantRepository.findById(applicant_id) ;
        Folder folder  =  applicant.getFolder() ;
        Set<Requirement> administratif = folder.getRequirements("ADMINISTRATIF") ;
        Set<Requirement> scientific = folder.getRequirements("SCIENTIFIC") ;
        Set<Requirement> autre = folder.getRequirements("AUTRE") ;

        modelAndView.addObject("folder",folder);
        modelAndView.addObject("administratif",administratif);
        modelAndView.addObject("scientific",scientific);
        modelAndView.addObject("autre",autre);
        modelAndView.addObject("applicant",applicant);

        modelAndView.setViewName("workspace/commission/show-folder");
        return modelAndView;


    }
    @GetMapping("/accepted-applicant-list")
    public String acceptedApplicantList(){return "workspace/commission/accepted-applicant-list";}
    @GetMapping("/evaluate-applicant")
    public String evaluateApplicant(){return "workspace/commission/evaluate-applicant";}



}
