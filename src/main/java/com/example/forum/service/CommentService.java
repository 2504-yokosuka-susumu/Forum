package com.example.forum.service;

import com.example.forum.controller.form.CommentForm;
import com.example.forum.repository.CommentRepository;
import com.example.forum.repository.entity.Comment;
import com.example.forum.repository.entity.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    /*
     * レコード追加
     */
    public void saveComment(CommentForm reqReport) {
        Comment saveComment = setCommentEntity(reqReport);
        commentRepository.save(saveComment);
    }

    /*
     * リクエストから取得した情報をEntityに設定
     */
    private Comment setCommentEntity(CommentForm reqComment) {
        Comment comment = new Comment();
        comment.setId(reqComment.getId());
        comment.setContent(reqComment.getComment());
        comment.setReportId(reqComment.getReportId());
        return comment;
    }
}
