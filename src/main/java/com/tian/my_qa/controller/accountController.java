package com.tian.my_qa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
