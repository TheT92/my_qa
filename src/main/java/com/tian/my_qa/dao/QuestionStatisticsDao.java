package com.tian.my_qa.dao;

import com.tian.my_qa.dto.QuestionStatisticsDto;
import com.tian.my_qa.model.QuestionStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface QuestionStatisticsDao extends JpaRepository<QuestionStatistics, Integer> {
    @Query(value = "SELECT * FROM question_statistics WHERE question_id = :questionId and user_id = :userId and del_flag != 1", nativeQuery = true)
    QuestionStatistics getItem(Integer questionId, Integer userId);

    default QuestionStatisticsDto getUserQuestionStatistics(Integer questionId, Integer userId) {
        QuestionStatistics result = getItem(questionId, userId);
        if(result == null) return null;
        QuestionStatisticsDto dto = new QuestionStatisticsDto();
        dto.setId(result.getId());
        dto.setTotalMarks(result.getTotalMarks());
        dto.setTotalCounts(result.getTotalCounts());
        dto.setUserId(userId);
        dto.setQuestionId(questionId);
        return dto;
    }


    @Modifying
    @Transactional
    @Query(value = "INSERT INTO question_statistics (question_id, user_id, total_marks, total_counts, del_flag) VALUES (:#{#dto.questionId}, :#{#dto.userId}, :#{#dto.totalMarks}, :#{#dto.totalCounts}, :#{#dto.delFlag});", nativeQuery = true)
    void insertData(@Param("dto") QuestionStatisticsDto dto);

    @Modifying
    @Transactional
    @Query(value = "UPDATE question_statistics q SET total_marks = :#{#dto.totalMarks}, total_counts = :#{#dto.totalCounts}  WHERE q.user_id = :#{#dto.userId} and q.question_id = :#{#dto.questionId} and q.del_flag != 1", nativeQuery = true)
    void updateData(@Param("dto") QuestionStatisticsDto dto);
}
