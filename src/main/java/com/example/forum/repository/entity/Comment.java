package com.example.forum.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String comment;

    @Column
    private int reportId;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public  int getReportId(){
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }
}
