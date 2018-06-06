package com.codebros.evaluator.workspace.model;
import javax.persistence.*;

@Entity
@Table(name = "attachment")
public class Attachment {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "attachment_id")
    private int id;

    @Column( name= "url")
    private String url  ;
    @Column( name= "valid")
    private Boolean valid  ;



}
