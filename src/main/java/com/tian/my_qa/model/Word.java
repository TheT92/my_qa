package com.tian.my_qa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String word;
    private String tips;
    private Integer type;
    private String imgUrl;
    private Integer delFlag;
    private Long createTime;
}
