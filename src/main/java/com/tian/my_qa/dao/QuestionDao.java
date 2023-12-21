package com.tian.my_qa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tian.my_qa.model.Question;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {
    @Query(value = "SELECT * FROM question q where q.question_title like %?1% and q.del_flag != 1 limit ?2 offset ?3", nativeQuery = true)
    List<Question> getQuestionsPage(String query, Integer limit, Integer offset);

    @Query(value = "SELECT count(*) FROM question q where q.question_title like %?1% and q.del_flag != 1", nativeQuery = true)
    Integer getQuestionsPageNum(String query);

    @Query(value = "SELECT * FROM question q where q.id = :id and q.del_flag != 1", nativeQuery = true)
    Question getQuestionDetail(int id);

    // Spring boot 中delete和 update需要添加Modifying注解
    @Modifying
    @Transactional
    @Query(value = "UPDATE question q SET del_flag=1 WHERE q.id = :id", nativeQuery = true)
    void deleteQuestion(int id);
}
