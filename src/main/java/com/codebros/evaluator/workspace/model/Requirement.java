package com.codebros.evaluator.workspace.model;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "requirement")
public class Requirement {


    public Requirement(){
        this.setValid(false);
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "requirement_id")
    private int id;


    @Column(name = "name")
    private  String name  ;


    @Column(name = "type")
    private  String type  ;

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Column(name = "valid")
    private  Boolean valid  ;

    public void setAttachments(Set<Attachment> attachments) {
        this.attachments = attachments;
    }

    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name="requirement_id")
    private Set<Attachment> attachments;

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

    public Set<Attachment> getAttachments() {
        return attachments;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
