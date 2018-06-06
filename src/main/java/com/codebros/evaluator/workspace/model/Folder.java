package com.codebros.evaluator.workspace.model;


import com.codebros.evaluator.workspace.repository.RequirementRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "folder")
public class Folder {



    public Folder(){

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "folder_id")
    private int id;

    public Set<Requirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(Set<Requirement> requirements) {
        this.requirements = requirements;
    }

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "folder_requirement", joinColumns = @JoinColumn(name = "folder_id"), inverseJoinColumns = @JoinColumn(name = "requirement_id"))
    private Set<Requirement> requirements;





    public Set<Requirement> getRequirements(String type) {
        Set<Requirement> new_reqs = new HashSet<Requirement>() ;
        for (Requirement req : this.requirements) {
             if (req.getType().equals(type)){
                 new_reqs.add(req) ;
             }
        }
        return new_reqs;
    }


}

