package com.tian.my_qa.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tian.my_qa.model.Question;
import com.tian.my_qa.model.UserAnswer;
import com.tian.my_qa.service.QuestionService;

@RestController
@RequestMapping("question")
public class QuestionController {
    @Autowired
    QuestionService qs;

    @GetMapping("{id}")
    public ResponseEntity<Question> getQuestion(@PathVariable int id, @RequestHeader(value = "token", required = true) String token) {
        return qs.findById(id);
    }

    @GetMapping("questionsPage")
    public ResponseEntity<Map<String, Object>> getQuestionsPage(@RequestParam String query, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return qs.getQuestionsPage(query, pageNum, pageSize);
    }

    @PostMapping("addQuestion")
    public ResponseEntity<String> addQuestion(@RequestBody Question question, @RequestHeader(value = "token", required = true) String token) {
        return qs.addQuestion(question, token);
    }

    @PostMapping("editQuestion")
    public ResponseEntity<String> editQuestion(@RequestBody Question question) {
        return qs.editQuestion(question);
    }

    @GetMapping("delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable int id) {
        return qs.deleteQuestion(id);
    }

    @PostMapping("submitAnswer")
    public ResponseEntity<String> submitAnswer(@RequestBody UserAnswer userAnswer, @RequestHeader(value = "token", required = true) String token) {
        return qs.submitAnswer(userAnswer, token);
    }

}
