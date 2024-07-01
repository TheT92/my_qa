package com.tian.my_qa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tian.my_qa.model.Question;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {
    @Query(value = "SELECT * FROM question q where q.question_title like %:query% and q.del_flag != 1 order by id desc limit :limit offset :offset", nativeQuery = true)
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

    // 获取上一题
    @Query(value = "SELECT q.id FROM question q WHERE q.id < :currentId and q.del_flag != 1 ORDER BY q.id DESC LIMIT 1", nativeQuery = true)
    Integer findPrevQuestionId(@Param("currentId") Integer currentId);

    // 获取下一题
    @Query(value = "SELECT q.id FROM question q WHERE q.id > :currentId and q.del_flag != 1 ORDER BY q.id ASC LIMIT 1", nativeQuery = true)
    Integer findNextQuestionId(@Param("currentId") Integer currentId);
}
