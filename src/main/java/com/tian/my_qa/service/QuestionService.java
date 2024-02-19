/*
 * @name: 
 * @param: 
 * @return: 
 */
package com.tian.my_qa.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tian.my_qa.dao.QuestionDao;
import com.tian.my_qa.dao.UserAnswerDao;
import com.tian.my_qa.model.Question;
import com.tian.my_qa.model.UserAnswer;
import com.tian.my_qa.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;

@Service
public class QuestionService {
    @Autowired
    QuestionDao qd;
    @Autowired
    UserAnswerDao ud;

    // 查询所有题目
    // spring.jpa.hibernate.ddl-auto=create 每次会重新建表，且会执行import.sql
    public ResponseEntity<Map<String, Object>> getQuestionsPage(String query, Integer pageNum, Integer pageSize) {
        try {
            Map<String, Object> res = new HashMap<>();
            Integer offset = (pageNum > 1 ? pageNum - 1 : 0) * pageSize;
            List<Question> list = qd.getQuestionsPage(query, pageSize, offset);
            Integer total = qd.getQuestionsPageNum(query);
            res.put("list", list);
            res.put("total", total);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
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
    public ResponseEntity<Question> findById(int id) {
        try {
            Question question = qd.getQuestionDetail(id);
            return new ResponseEntity<>(question, HttpStatus.OK);
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
    public ResponseEntity<String> submitAnswer(UserAnswer userAnswer, String token) {
        try {
            String id = JwtUtil.getMemberIdByJwtToken(token);
            userAnswer.setUserId(Integer.parseInt(id));
            userAnswer.setCreateTime(System.currentTimeMillis());
            ud.save(userAnswer);
            return new ResponseEntity<>("success", HttpStatus.CREATED);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>("fail", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
        }
    }
}
