/*
 * @name: 
 * @param: 
 * @return: 
 */
package com.tian.my_qa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tian.my_qa.dao.QuestionDao;
import com.tian.my_qa.model.Question;
import com.tian.my_qa.util.JwtUtil;

@Service
public class QuestionService {
    @Autowired
    QuestionDao qd;

    // 查询所有题目
    // spring.jpa.hibernate.ddl-auto=create 每次会重新建表，且会执行import.sql
    public ResponseEntity<List<Question>> getAllQuestions(String query) {
        try {
            return new ResponseEntity<>(qd.getQuestionList(query), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    // 新增题目
    public ResponseEntity<String> addQuestion(Question question, String token) {
        try {
            if (!token.isEmpty()) {
                String id = JwtUtil.getMemberIdByJwtToken(token);
                question.setCreatedBy(Integer.parseInt(id));
                qd.save(question);
                return new ResponseEntity<>("success", HttpStatus.CREATED);
            } else {
                throw new Exception("No token");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
    }

    // 修改题目
    public ResponseEntity<String> editQuestion(Question question) {
        try {
            qd.save(question);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
    }

    // 查询详情
    public ResponseEntity<Question> findById(int id) {
        try {
            Question question = qd.getQuestionDetail(id);
            return new ResponseEntity<>(question, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new Question(), HttpStatus.BAD_REQUEST);
    }

    // 删除问题
    public ResponseEntity<String> deleteQuestion(int id) {
        try {
            qd.deleteQuestion(id);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
    }
}
