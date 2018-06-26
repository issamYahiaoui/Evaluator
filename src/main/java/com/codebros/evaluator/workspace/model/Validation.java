package com.codebros.evaluator.workspace.model;


import javax.persistence.*;

@Entity
public class Validation {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFolder_id() {
        return folder_id;
    }

    public void setFolder_id(int folder_id) {
        this.folder_id = folder_id;
    }

    public int getRequirement_id() {
        return requirement_id;
    }

    public void setRequirement_id(int requirement_id) {
        this.requirement_id = requirement_id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "validation_id")
    private int id;



    @Column(name = "folder_id")
    private int folder_id;


    @Column(name = "requirement_id")
    private int requirement_id;

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Column(name = "valid")
    private Boolean valid;



}
