package com.tian.my_qa.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tian.my_qa.dao.AccountDao;
import com.tian.my_qa.model.Account;
import com.tian.my_qa.model.LoginUser;
import com.tian.my_qa.util.JwtUtil;

@Service
public class AccountService {
    @Autowired
    AccountDao accountDao;

    public ResponseEntity<String> getToken(LoginUser loginUser) {
        try {
            Account account = accountDao.getAccount(loginUser.getLoginAccount(), loginUser.getPassword());
            if (account != null && account.getLoginAccount() != null) {
                int id = account.getId();
                String token = JwtUtil.getJwtToken(id, loginUser.getLoginAccount());
                return new ResponseEntity<>(token, HttpStatus.OK);
            }
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Account> getUserInfo(int id) {
        try {
            Account account = accountDao.getUserInfo(id);
            if (account != null && account.getLoginAccount() != null) {
                account.setDelFlag(null);
                account.setLoginAccount(null);
                account.setPassword(null);
                account.setRoleId(null);
                return new ResponseEntity<>(account, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
