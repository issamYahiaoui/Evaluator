package com.codebros.evaluator.workspace.repository;

import com.codebros.evaluator.workspace.model.Requirement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository()
public interface RequirementRepository extends JpaRepository<Requirement,Long>{

    Requirement findByName(String name);
    ArrayList<Requirement> findAllByType(String type);
}