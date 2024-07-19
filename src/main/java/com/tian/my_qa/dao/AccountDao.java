package com.tian.my_qa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tian.my_qa.dto.AnswerHistoryDto;
import com.tian.my_qa.model.Account;

@Repository
public interface AccountDao extends JpaRepository<Account, Integer> {
    @Query(value = "SELECT * FROM account a where a.login_account = :loginAccount and a.password = :password and a.del_flag != 1", nativeQuery = true)
    Account getAccount(String loginAccount, String password);

    @Query(value = "SELECT * FROM account a where a.id = :id and a.del_flag != 1", nativeQuery = true)
    Account getUserInfo(int id);

    
    @Query(value = "SELECT u.id, TO_CHAR(TO_TIMESTAMP(u.create_time / 1000), 'YYYY-MM-DD HH24:MI:SS') as createTime, u.rating, q.question_title as questionName FROM user_answer u left join question q on u.question_id = q.id where u.user_id = :userId order by id desc limit :limit offset :offset", nativeQuery = true)
    List<AnswerHistoryDto> getAnsewerHistoryPage(Integer limit, Integer offset, int userId);

    @Query(value = "SELECT count(*) FROM user_answer u where u.user_id = :userId", nativeQuery = true)
    Integer getAnsewerHistoryPageNum(int userId);
}
