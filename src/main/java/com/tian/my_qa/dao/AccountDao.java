package com.tian.my_qa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tian.my_qa.model.Account;

public interface AccountDao extends JpaRepository<Account, Integer> {
    @Query(value = "SELECT * FROM account a where a.login_account = :loginAccount and a.password = :password and a.del_flag != 1", nativeQuery = true)
    Account getAccount(String loginAccount, String password);
}
