package com.codebros.evaluator.workspace.repository;

import com.codebros.evaluator.workspace.model.Etablissement;
import com.codebros.evaluator.workspace.model.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtablissementRepository extends JpaRepository<Etablissement,Long> {

    Etablissement findById(Integer id) ;

}
