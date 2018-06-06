package com.codebros.evaluator.workspace.model;
import javax.persistence.*;

@Entity
@Table(name = "commission")
public class Commission{
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "commission_id")
    private int id;

}
