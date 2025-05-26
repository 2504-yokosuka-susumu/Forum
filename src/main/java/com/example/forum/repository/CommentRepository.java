package com.example.forum.repository;

import com.example.forum.repository.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    public List<Comment> findAllByOrderByUpdatedDateDesc();
    public List<Comment> findByUpdatedDateBetweenOrderByUpdatedDateDesc(Date startTime, Date endTime);
}