package com.codebros.evaluator.workspace.repository;

import com.codebros.evaluator.workspace.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository  extends JpaRepository<Application, Long>{

}
