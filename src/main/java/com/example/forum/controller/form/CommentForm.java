package com.example.forum.controller.form;

import java.util.Date;

public class CommentForm {

    private int id;
    private String comment;
    private int reportId;
    private Date createdDate;
    private Date updatedDate;

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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
