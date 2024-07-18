package com.tian.my_qa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tian.my_qa.dto.QuestionListDto;
import com.tian.my_qa.model.Question;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {
    @Query(value = "SELECT q.id, q.category, q.question_title as questionTitle, COALESCE(c.total_counts, 0) as totalCounts, (CASE WHEN c.total_counts IS NOT NULL AND c.total_counts > 5 THEN (c.total_marks / c.total_counts) * 20 ELSE NULL END) AS correctRate FROM question q left join question_statistics c on c.question_id = q.id and c.user_id = :userId where q.question_title like %:query% and q.category like %:tag% and q.del_flag != 1 order by id desc limit :limit offset :offset", nativeQuery = true)
    List<QuestionListDto> getQuestionsPage(String tag, String query, Integer limit, Integer offset, int userId);

    @Query(value = "SELECT count(*) FROM question q where q.question_title like %?2% and q.category like %?1%  and q.del_flag != 1", nativeQuery = true)
    Integer getQuestionsPageNum(String tag, String query);

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
