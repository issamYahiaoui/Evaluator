package com.codebros.evaluator.auth.service;


import com.codebros.evaluator.auth.model.Role;
import com.codebros.evaluator.auth.model.User;
import com.codebros.evaluator.auth.repository.RoleRepository;
import com.codebros.evaluator.auth.repository.UserRepository;
import com.codebros.evaluator.workspace.model.*;
import com.codebros.evaluator.workspace.repository.*;
import com.codebros.evaluator.workspace.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;


@Service("seederService")
public class SeederServiceImpl implements SeederService{



    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RequirementRepository requirementRepository;

    @Autowired
    private FolderRepository folderRepository ;
    @Autowired
    private EtablissementRepository etablissementRepository ;
    @Autowired
    private SpecialityRepository specialityRepository ;

    @Autowired
    private FolderService folderService ;

    @Autowired
    private ApplicantRepository applicantRepository ;

    @Autowired
    private ApplicationRepository applicationRepository;








    public void seed(){







        // requirements

        this.saveRequirementSeed( "ADMINISTRATIF", "Demande manuscrite");
        this.saveRequirementSeed( "ADMINISTRATIF", "Attestation du travail");
        this.saveRequirementSeed( "ADMINISTRATIF", "Copie du diplôme de doctorat d’Etat");
        this.saveRequirementSeed( "ADMINISTRATIF", "Copie de l’attestation d’équivalence _  dans le cas d’un diplôme étranger ");
        this.saveRequirementSeed( "ADMINISTRATIF", "Copie de l’arrêté de maître de conférences classe A");
        this.saveRequirementSeed( "ADMINISTRATIF", "CURRICULUM VITAE DÉTAILLÉ");
        this.saveRequirementSeed( "ADMINISTRATIF", "Copies des décrets et ou des arrêtés de nomination à des fonctions ou postes supérieurs");
        this.saveRequirementSeed( "ADMINISTRATIF", "Copie de la thèse de Doctorat");


        this.saveRequirementSeed("SCIENTIFIC", "Compte rendu des activités pédagogiques et scientifiques ");
        this.saveRequirementSeed( "SCIENTIFIC", "copies des autorisations de soutenance");
        this.saveRequirementSeed( "SCIENTIFIC", "copies des procès-verbaux de soutenance");
        this.saveRequirementSeed( "SCIENTIFIC", "Exemplaires des ouvrages pédagogiques édités");
        this.saveRequirementSeed( "SCIENTIFIC", "Exemplaires des manuels pédagogiques édités");
        this.saveRequirementSeed( "SCIENTIFIC", "Copies des pages de garde des mémoires de fin d’études de masters encadrés et soutenus");
        this.saveRequirementSeed( "SCIENTIFIC", "Exemplaires des polycopiés édités");
        this.saveRequirementSeed( "SCIENTIFIC", "Copies des pages de garde des mémoires de magister et ou thèses de doctorats ou doctorats d’Etat dirigés et soutenus");


        this.saveRequirementSeed( "AUTRE", "Brevet d’invention éventuellement");
        this.saveRequirementSeed( "AUTRE", "Communications internationales, exemplaires originaux –tiré à part-");
        this.saveRequirementSeed( "AUTRE", "Communications nationales, exemplaires originaux –tiré à part-");
        this.saveRequirementSeed( "AUTRE", "Brevet d’invention éventuellement");
        this.saveRequirementSeed( "AUTRE", "Publications nationales _ revues, périodiques, ouvrages, actes et proceedings édités , exemplaires originaux –tiré à part-");
        this.saveRequirementSeed( "AUTRE", "Activités d’animation scientifique  : organisation de colloques, expertise, membre de comité de lecture, chef ou membre d’un projet de recherche ");




        /***** etablissements  ***/

        this.saveEtablissementSeeder(1,"ECOLE NATIONNALE SUPERIEURE D'INFORMATIQUE ");
        this.saveEtablissementSeeder(2,"ECOLE NATIONNALE SUPERIEURE POLYTECHNIQUE");
        this.saveEtablissementSeeder(3,"ECOLE NATIONNALE SUPERIEURE D'ENSEIGNEMENT");
        this.saveEtablissementSeeder(4,"FAULTE DE MEDECINE");
        this.saveEtablissementSeeder(5,"FAULTE DE PHARMACIE");

        /**** specialities ****/

        this.saveSpecialitySeeder(1,"INFORMATIQUE");
        this.saveSpecialitySeeder(2,"MEDECINE");
        this.saveSpecialitySeeder(3,"TELECOMMUNICATION");
        this.saveSpecialitySeeder(4,"PHYSIQUE");
        this.saveSpecialitySeeder(5,"MATHEMATIQUE");





        //roles
        this.saveRoleSeed(1,"ADMIN");
        this.saveRoleSeed(2,"APPLICANT");
        this.saveRoleSeed(3,"COMMISSION");







       Application app1 =  this.saveApplication("Yahiaoui", "Issam", "Diplome" , etablissementRepository.findById(1),specialityRepository.findById(1),"066421310");
        Application app2= this.saveApplication("Setrerrahmane", "Djalle", "Diplome" , etablissementRepository.findById(2),specialityRepository.findById(2),"066421310");
        Application app3= this.saveApplication("Amara", "Ziyad", "Diplome" , etablissementRepository.findById(3),specialityRepository.findById(3),"066421310");

        //users
        this.saveUserSeed("admin@gmail.com","admin","123456","ADMIN", new Application());
        this.saveUserSeed("applicant@gmail.com","applicant","123456","APPLICANT", app1);
        this.saveUserSeed("applicant2@gmail.com","applicant 2","123456","APPLICANT", app2);
        this.saveUserSeed("commission@gmail.com","commission","123456","COMMISSION", new Application());


    }












    private void  saveApplicant(User user, Application application){
        Applicant applicant = new Applicant();
        Folder folder = new Folder();
        applicant.setUser(user);
        folder = folderService.init(folder) ;
        folderService.save(folder) ;
        System.out.println(requirementRepository.findAll());
        applicant.setFolder(folderService.init(folder)) ;
        applicant.setAppliaction(application);
        applicantRepository.save(applicant);
    }


    private void saveUserSeed(String email ,String name,  String password , String role, Application application ){
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setActive(1);
        Role userRole = roleRepository.findByRole(role);

        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
        switch (role){
            case "ADMIN":

                return ;
            case "APPLICANT":
                this.saveApplicant(user,application);
                return ;
            case "COMMISSION":

                return ;
        }
    }
    private void  saveRoleSeed(Integer role_id , String role ){
        Role mRole = new Role() ;
        mRole.setId(role_id);
        mRole.setRole(role);
        roleRepository.save(mRole);
    }


    public  void saveRequirementSeed(String type, String name){
        Requirement req =  new Requirement() ;
        req.setName(name);
        req.setType(type);
        requirementRepository.save(req) ;
    }


    public  void saveEtablissementSeeder(Integer id, String name){
        Etablissement et =  new Etablissement() ;
        et.setId(id);
        et.setName(name);
        etablissementRepository.save(et);
    }
    public  void saveSpecialitySeeder(Integer id, String name){
        Speciality et =  new Speciality() ;
        et.setId(id);
        et.setName(name);
        specialityRepository.save(et);
    }

    public  Application saveApplication(String name , String firstname ,  String diplome , Etablissement etablissement ,  Speciality speciality, String phone ){
        Application application =  new Application() ;
        application.setName(name);
        application.setFirstName(firstname);
        application.setEtablissement(etablissement);
        application.setSpeciality(speciality);
        application.setDoctorat_habilitation_universitaire(diplome);
        application.setPhone(phone);
        applicationRepository.save(application) ;
        return application ;
    }

}