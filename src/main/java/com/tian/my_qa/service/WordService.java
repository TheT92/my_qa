package com.tian.my_qa.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tian.my_qa.dao.WordDao;
import com.tian.my_qa.model.Word;

import io.jsonwebtoken.ExpiredJwtException;

@Service
public class WordService {
    @Autowired
    WordDao wordDao;

    // 新增题目
    public ResponseEntity<String> addWord(Word word, String token) {
        try {
            word.setDelFlag(0);
            word.setCreateTime(System.currentTimeMillis());
            wordDao.save(word);
            return new ResponseEntity<>("success", HttpStatus.CREATED);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>("fail", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Map<String, Object>> getWordPage(String query, Integer pageNum, Integer pageSize) {
        try {
            Map<String, Object> res = new HashMap<>();
            Integer offset = (pageNum > 1 ? pageNum - 1 : 0) * pageSize;
            List<Word> list = wordDao.getWordPage(query, pageSize, offset);
            Integer total = wordDao.getWordPageNum(query);
            res.put("list", list);
            res.put("total", total);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
