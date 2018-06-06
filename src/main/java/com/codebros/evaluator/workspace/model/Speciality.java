package com.codebros.evaluator.workspace.model;


import javax.persistence.*;

@Entity
public class Speciality {


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "speciality_id")
    private int id;


    @Column(name="name")
    private String name  ;
}
