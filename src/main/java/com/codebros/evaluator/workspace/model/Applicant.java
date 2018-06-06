package com.codebros.evaluator.workspace.model;

import com.codebros.evaluator.auth.model.User;

import javax.persistence.*;


@Entity
@Table(name = "applicant")
public class Applicant {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public Application getAppliaction() {
        return appliaction;
    }

    public void setAppliaction(Application appliaction) {
        this.appliaction = appliaction;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "applicant_id")
    private int id;



    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id")
    private Folder folder;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private Application appliaction;

}
