package com.codebros.evaluator.workspace.repository;

import com.codebros.evaluator.workspace.model.Attachment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



public interface AttachmentRepository extends JpaRepository<Attachment,Long>{
    Attachment findById(Integer id);
    Attachment save(Attachment attachment) ;
}