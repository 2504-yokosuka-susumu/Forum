package com.example.forum.controller.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReportForm {

    @NotBlank(message="投稿内容を入力してください")
    @Size(min=10, max= 100)
    private String content;
    private int id;
    private Date createdDate;
    private Date updatedDate;
}
