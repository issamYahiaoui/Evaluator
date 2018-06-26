package com.codebros.evaluator.workspace.repository;

import com.codebros.evaluator.workspace.model.Evaluation;
import com.codebros.evaluator.workspace.model.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationRepository extends JpaRepository<Evaluation,Long>{
    Evaluation findById(Integer id);
    Evaluation save(Evaluation folder) ;
}