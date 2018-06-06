package com.codebros.evaluator.workspace.Controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/commission")
public class CommissionController {


    @GetMapping("/applicant-list")
    public String applicantList (){return "workspace/commission/applicant-list" ; }

    @GetMapping("/show-folder")
    public String showFolder(){return "workspace/commission/show-folder";}
    @GetMapping("/accepted-applicant-list")
    public String acceptedApplicantList(){return "workspace/commission/accepted-applicant-list";}
    @GetMapping("/evaluate-applicant")
    public String evaluateApplicant(){return "workspace/commission/evaluate-applicant";}



}
