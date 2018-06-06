package com.codebros.evaluator.workspace.repository;

import com.codebros.evaluator.workspace.model.Folder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



public interface FolderRepository extends JpaRepository<Folder,Long>{
    Folder findById(Integer id);
    Folder save(Folder folder) ;
}