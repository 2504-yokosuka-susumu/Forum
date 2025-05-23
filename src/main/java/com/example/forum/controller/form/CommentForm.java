package com.example.forum.controller.form;

public class CommentForm {

    private int id;
    private String comment;
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
