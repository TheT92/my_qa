package com.tian.my_qa.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tian.my_qa.model.UserAnswer;

public interface UserAnswerDao extends JpaRepository<UserAnswer, Integer> {

}
