package com.codebros.evaluator.auth.service;


import java.util.Arrays;
import java.util.HashSet;

import com.codebros.evaluator.auth.model.Role;
import com.codebros.evaluator.auth.model.User;
import com.codebros.evaluator.auth.repository.RoleRepository;
import com.codebros.evaluator.auth.repository.UserRepository;
import com.codebros.evaluator.workspace.model.Applicant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service("userService")
public class UserServiceImpl implements UserService{



    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }


    public void seed(){
        this.saveRoleSeed(1,"ADMIN");
        this.saveRoleSeed(2,"APPLICANT");
        this.saveRoleSeed(3,"COMMISSION");

        this.saveUserSeed("admin@gmail.com","admin","123456","ADMIN");
        this.saveUserSeed("applicant@gmail.com","applicant","123456","APPLICANT");
        this.saveUserSeed("commission@gmail.com","commission","123456","COMMISSION");


    }




    private void saveUserSeed(String email ,String name,  String password , String role ){
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setActive(1);
        Role userRole = roleRepository.findByRole(role);
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }
    private void  saveRoleSeed(Integer role_id , String role ){
        Role mRole = new Role() ;
        mRole.setId(role_id);
        mRole.setRole(role);
        roleRepository.save(mRole);
    }





}