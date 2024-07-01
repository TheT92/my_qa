package com.tian.my_qa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.tian.my_qa.model.UserAnswer;

public interface UserAnswerDao extends JpaRepository<UserAnswer, Integer> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_answer (rating, user_id, question_id, create_time) VALUES (:rating, :userId, :questionId, :createTime);", nativeQuery = true)
    void saveAnswer(@Param("rating") Double rating,
            @Param("userId") Integer userId,
            @Param("questionId") Integer questionId,
            @Param("createTime") Long createTime);
}
