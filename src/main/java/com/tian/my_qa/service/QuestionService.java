/*
 * @name: 
 * @param: 
 * @return: 
 */
package com.tian.my_qa.service;

import java.util.List;
import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tian.my_qa.dao.QuestionDao;
import com.tian.my_qa.dao.QuestionStatisticsDao;
import com.tian.my_qa.dao.UserAnswerDao;
import com.tian.my_qa.dto.Pager;
import com.tian.my_qa.dto.QuestionDto;
import com.tian.my_qa.dto.QuestionListDto;
import com.tian.my_qa.dto.QuestionStatisticsDto;
import com.tian.my_qa.dto.UserAnswerDto;
import com.tian.my_qa.model.Question;
import com.tian.my_qa.util.JwtUtil;
import com.tian.my_qa.util.Util;

import io.jsonwebtoken.ExpiredJwtException;

@Service
public class QuestionService {
    @Autowired
    QuestionDao qd;
    @Autowired
    UserAnswerDao ud;
    @Autowired
    QuestionStatisticsDao qsd;

    // 查询所有题目
    // spring.jpa.hibernate.ddl-auto=create 每次会重新建表，且会执行import.sql
    public ResponseEntity<Pager<QuestionListDto>> getQuestionsPage(String tag, String query, Integer pageNum, Integer pageSize, String token) {
        try {
            String userId = Util.getUserIdByToken.apply(token);
            Integer offset = Util.getPageOffset.apply(pageNum, pageSize);
            List<QuestionListDto> list = qd.getQuestionsPage(tag, query, pageSize, offset, Integer.parseInt(userId));
            Integer total = qd.getQuestionsPageNum(tag, query);
            BiFunction<List<QuestionListDto>, Integer, Pager<QuestionListDto>> s = Pager::new;
            Pager<QuestionListDto> pager = s.apply(list, total);
            return new ResponseEntity<>(pager, HttpStatus.OK);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // 新增题目
    public ResponseEntity<String> addQuestion(Question question, String token) {
        try {
            String id = JwtUtil.getMemberIdByJwtToken(token);
            question.setCreatedBy(Integer.parseInt(id));
            question.setDelFlag(0);
            question.setCreateTime(System.currentTimeMillis());
            qd.save(question);
            return new ResponseEntity<>("success", HttpStatus.CREATED);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>("fail", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
        }
    }

    // 修改题目
    public ResponseEntity<String> editQuestion(Question question) {
        try {
            qd.save(question);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
        }
    }

    // 查询详情
    public ResponseEntity<QuestionDto> findById(int id) {
        try {
            Question question = qd.getQuestionDetail(id);
            Integer prev = qd.findPrevQuestionId(id);
            Integer next = qd.findNextQuestionId(id);
            QuestionDto dto = new QuestionDto(question, prev, next);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // 删除问题
    public ResponseEntity<String> deleteQuestion(int id) {
        try {
            qd.deleteQuestion(id);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
        }
    }

    // 用户答题
    public ResponseEntity<String> submitAnswer(UserAnswerDto dto, String token) {
        try {
            String userId = JwtUtil.getMemberIdByJwtToken(token);
            ud.saveAnswer(dto.getRating(), Integer.parseInt(userId), dto.getQuestionId(), System.currentTimeMillis());

            // 更新统计信息
            QuestionStatisticsDto stats = qsd.getUserQuestionStatistics(dto.getQuestionId(), Integer.parseInt(userId));
            if (stats == null) {
                stats = new QuestionStatisticsDto();
                stats.setQuestionId(dto.getQuestionId());
                stats.setUserId(Integer.parseInt(userId));
                stats.setTotalCounts(1);
                stats.setTotalMarks(dto.getRating());
                stats.setDelFlag(0);
                qsd.insertData(stats);
            } else {
                stats.setTotalCounts(stats.getTotalCounts() + 1);
                stats.setTotalMarks(stats.getTotalMarks() + dto.getRating());
                qsd.updateData(stats);
            }
            return new ResponseEntity<>("success", HttpStatus.CREATED);
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            return new ResponseEntity<>("fail", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
        }
    }
}
