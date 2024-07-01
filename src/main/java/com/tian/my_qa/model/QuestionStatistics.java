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
public class QuestionStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "question_id", foreignKey = @ForeignKey(name = "FK_QUESTION_ID"))
    private Question question;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_USER_CORRECT_RATE"))
    private Account account;

    private double totalMarks;
    private Integer totalCounts;
    private Integer delFlag;

    public Double getCorrectRate() {
        return totalCounts <= 5 ? -1 : (double) totalMarks / totalCounts;
    }
}
