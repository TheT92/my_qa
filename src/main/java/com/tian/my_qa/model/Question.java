/*
 * @name: 
 * @param: 
 * @return: 
 */
package com.tian.my_qa.model;

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
    private String rightAnswer;
    private Integer createdBy;
    private Integer delFlag;
}

