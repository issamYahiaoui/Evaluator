package com.codebros.evaluator.auth.repository;





import com.codebros.evaluator.auth.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository()
public interface RoleRepository extends JpaRepository<Role, Integer>{
    Role findByRole(String role);

}