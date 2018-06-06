package com.codebros.evaluator.auth.service;

import com.codebros.evaluator.auth.model.User;
import com.codebros.evaluator.workspace.model.Applicant;

public interface UserService {
    public User findUserByEmail(String email);
    public void saveUser(User user);
    public void seed();
}