package com.codebros.evaluator.workspace.service;


import com.codebros.evaluator.workspace.model.Folder;
import com.codebros.evaluator.workspace.model.Requirement;
import com.codebros.evaluator.workspace.repository.FolderRepository;
import com.codebros.evaluator.workspace.repository.RequirementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service("FolderService")
public class FolderServiceImpl  implements  FolderService {

    @Autowired
    RequirementRepository requirementRepository ;

    @Autowired
    FolderRepository folderRepository ;

    public Folder init(Folder folder ){
        Set<Requirement> requirements = new HashSet<Requirement>();
        requirements.addAll(requirementRepository.findAll()) ;
        System.out.print(requirements.toString());
        folder.setRequirements(requirements);
        return folder;
    }
    public Folder save(Folder folder) {
        return folderRepository.save(folder);
    }

}
