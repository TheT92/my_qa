package com.tian.my_qa.dto;

import com.tian.my_qa.model.Question;

import lombok.Data;

@Data
public class QuestionDto {
    private Question question;
    private Integer prev;
    private Integer next;

    public QuestionDto(Question question, Integer prev, Integer next) {
        this.question = question;
        this.prev = prev;
        this.next = next;
    }
}
