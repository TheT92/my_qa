package com.tian.my_qa.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tian.my_qa.dao.AccountDao;
import com.tian.my_qa.dto.AnswerHistoryDto;
import com.tian.my_qa.dto.Pager;
import com.tian.my_qa.model.Account;
import com.tian.my_qa.model.LoginUser;
import com.tian.my_qa.util.JwtUtil;
import com.tian.my_qa.util.Util;

import io.jsonwebtoken.ExpiredJwtException;

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
    
    // 获取用户做题的历史分页
    public ResponseEntity<Pager<AnswerHistoryDto>> getAnsewerHistoryPage(Integer pageNum, Integer pageSize, String token) {
        try {
            String userId = Util.getUserIdByToken.apply(token);
            Integer offset = Util.getPageOffset.apply(pageNum, pageSize);
            List<AnswerHistoryDto> list = accountDao.getAnsewerHistoryPage(pageSize, offset, Integer.parseInt(userId));
            Integer total = accountDao.getAnsewerHistoryPageNum(Integer.parseInt(userId));
            BiFunction<List<AnswerHistoryDto>, Integer, Pager<AnswerHistoryDto>> s = Pager::new;
            Pager<AnswerHistoryDto> pager = s.apply(list, total);
            return new ResponseEntity<>(pager, HttpStatus.OK);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Map<String, Object>> getUserStatistics(String token) {
        try {
            String userId = Util.getUserIdByToken.apply(token);
            // Map<String, Object> res = Util.wrapPageData.apply(list, total);
            // return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
