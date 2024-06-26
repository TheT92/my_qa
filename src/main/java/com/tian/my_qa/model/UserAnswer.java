package com.tian.my_qa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class UserAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_USER_CORRECT_RATE"))
    private Account account;

    @ManyToOne
    @JoinColumn(name = "question_id", foreignKey = @ForeignKey(name = "FK_QUESTION_ID"))
    private Question question;

    private Double rating;
    private Long createTime;
}
