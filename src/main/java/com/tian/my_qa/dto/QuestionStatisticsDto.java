package com.tian.my_qa.dto;

import lombok.Data;

@Data
public class QuestionStatisticsDto {
    private Integer id;
    private Integer questionId;
    private Integer userId;
    private Double totalMarks;
    private Integer totalCounts;
    private Integer delFlag;
}
