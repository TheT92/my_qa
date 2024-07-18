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

import com.tian.my_qa.model.Word;
import com.tian.my_qa.service.WordService;

@RestController
@RequestMapping("word")
public class WordController {
    @Autowired
    WordService wordService;

    @GetMapping("{id}")
    public ResponseEntity<Word> getWord(@PathVariable int id, @RequestHeader(value = "token", required = true) String token) {
        return wordService.findById(id);
    }

    @PostMapping("add")
    public ResponseEntity<String> addWord(@RequestBody Word word, @RequestHeader(value = "token", required = true) String token) {
        return wordService.addWord(word, token);
    }

    @PostMapping("edit")
    public ResponseEntity<String> editWord(@RequestBody Word word, @RequestHeader(value = "token", required = true) String token) {
        return wordService.editWord(word);
    }

    @GetMapping("page")
    public ResponseEntity<Map<String, Object>> getWordPage(@RequestParam String query, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return wordService.getWordPage(query, pageNum, pageSize);
    }
}
