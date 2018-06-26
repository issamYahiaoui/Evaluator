package com.codebros.evaluator.workspace.model;


import com.codebros.evaluator.auth.model.User;

import javax.persistence.*;

@Entity
public class Evaluation {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public User getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(User evaluator) {
        this.evaluator = evaluator;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "evaluation_id")
    private int id;



    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id")
    private Folder folder;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User evaluator;

    @Column(name = "note")
    private int note;

    @Column(name = "remark")
    private String remark ;


}
