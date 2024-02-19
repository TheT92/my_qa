package com.tian.my_qa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tian.my_qa.model.Word;

@Repository
public interface WordDao extends JpaRepository<Word, Integer> {
    @Query(value = "SELECT * FROM word w where w.word like %:query% and w.del_flag != 1 order by id desc limit :limit offset :offset", nativeQuery = true)
    List<Word> getWordPage(String query, Integer limit, Integer offset);

    @Query(value = "SELECT count(*) FROM word w where w.word like %?1% and w.del_flag != 1", nativeQuery = true)
    Integer getWordPageNum(String query);
}
