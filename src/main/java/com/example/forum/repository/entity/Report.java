package com.example.forum.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "report")
@Getter
@Setter
public class Report {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String content;

    @Column(name = "created_date", insertable = false, updatable = false)
    private Date createdDate;

    @Column(name = "updated_date", insertable = false, updatable = true)
    private Date updatedDate;
}
