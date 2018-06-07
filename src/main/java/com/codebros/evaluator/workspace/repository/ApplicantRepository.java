package com.codebros.evaluator.workspace.repository;

import com.codebros.evaluator.auth.model.User;
import com.codebros.evaluator.workspace.model.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicantRepository extends JpaRepository<Applicant,Long> {


   public Applicant findByUser(User user);
   public Applicant findById(Integer id);
}
