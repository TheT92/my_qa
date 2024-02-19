package com.tian.my_qa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String category;
    private String questionTitle;
    private String questionContent;
    @Column(length = 2048)
    private String rightAnswer;
    private Integer createdBy;
    private Integer delFlag;
    private Long createTime;
}

