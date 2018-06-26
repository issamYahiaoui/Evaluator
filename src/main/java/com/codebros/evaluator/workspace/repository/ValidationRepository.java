package com.codebros.evaluator.workspace.repository;

import com.codebros.evaluator.workspace.model.Evaluation;
import com.codebros.evaluator.workspace.model.Validation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValidationRepository extends JpaRepository<Validation,Long> {
    Validation findById(Integer id);
    Validation save(Validation folder) ;
}