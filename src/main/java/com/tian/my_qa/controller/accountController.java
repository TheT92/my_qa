package com.tian.my_qa.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tian.my_qa.dto.AnswerHistoryDto;
import com.tian.my_qa.dto.Pager;
import com.tian.my_qa.model.Account;
import com.tian.my_qa.model.LoginUser;
import com.tian.my_qa.service.AccountService;
import com.tian.my_qa.util.JwtUtil;

@RestController
@RequestMapping("account")
public class AccountController {
    @Autowired
    AccountService accountService;
     // 模拟登录请求
    @PostMapping("/login")
    public ResponseEntity<String> login( @RequestBody LoginUser loginUser) {
        return accountService.getToken(loginUser);
    }

     // 获取用户信息
     @GetMapping("/user")
     public ResponseEntity<Account> user(@RequestHeader(value = "token", required = true) String token) {
        String id = JwtUtil.getMemberIdByJwtToken(token); 
        return accountService.getUserInfo(Integer.parseInt(id));
     }

    // 从 token 中获取信息
    @GetMapping("/check")
    public String check(@RequestHeader(value = "token", required = false) String token) {
        String id = JwtUtil.getMemberIdByJwtToken(token);
        System.out.println("该 token 的 id 为： " + id);
        return id;
    }

    @GetMapping("/answerHistoryPage")
    public ResponseEntity<Pager<AnswerHistoryDto>> getAnsewerHistoryPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,  @RequestHeader(value = "token", required = true) String token) {
        return accountService.getAnsewerHistoryPage(pageNum, pageSize, token);
    }

    // 个人中心页面，获取各类统计数据
    @GetMapping("/getUserStatistics")
    public ResponseEntity<Map<String, Object>> getUserStatistics(@RequestHeader(value = "token", required = true) String token) {
        return accountService.getUserStatistics(token);
    }
}
