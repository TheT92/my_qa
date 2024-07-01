package com.tian.my_qa.dto;
import lombok.Data;
@Data
public class UserAnswerDto {
    private Integer id;
    private Integer userId;
    private Integer questionId;
    private Double rating;
    private Long createTime;
}
