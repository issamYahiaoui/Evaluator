package com.codebros.evaluator.workspace.model;


import javax.persistence.*;
import java.util.Set;

@Entity
public class Application {


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDoctorat_habilitation_universitaire() {
        return doctorat_habilitation_universitaire;
    }

    public void setDoctorat_habilitation_universitaire(String doctorat_habilitation_universitaire) {
        this.doctorat_habilitation_universitaire = doctorat_habilitation_universitaire;
    }

    public String getAttestation_equivalence() {
        return attestation_equivalence;
    }

    public void setAttestation_equivalence(String attestation_equivalence) {
        this.attestation_equivalence = attestation_equivalence;
    }

    public String getDate_doctorat_etat() {
        return date_doctorat_etat;
    }

    public void setDate_doctorat_etat(String date_doctorat_etat) {
        this.date_doctorat_etat = date_doctorat_etat;
    }

    public String getDate_displome_mca() {
        return date_displome_mca;
    }

    public void setDate_displome_mca(String date_displome_mca) {
        this.date_displome_mca = date_displome_mca;
    }



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "application_id")
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name= "name")
    private String name ;

    @Column(name= "firstName")
    private String firstName ;

    @Column(name="phone")
    private String phone  ;

    @Column(name="doctorat_habilitation_universitaire")
    private String doctorat_habilitation_universitaire ;

    @Column(name="attestation_equivalence")
    private String attestation_equivalence ;

    @Column(name="date_doctorat_etat")
    private String date_doctorat_etat ;

    @Column(name="date_displome_mca")
    private String date_displome_mca ;


    public Etablissement getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(Etablissement etablissement) {
        this.etablissement = etablissement;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etablissement_id")
    private Etablissement etablissement;


    @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "speciality_id")
    private Speciality speciality;



}
