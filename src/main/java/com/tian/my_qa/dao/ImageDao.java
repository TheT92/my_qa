package com.tian.my_qa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tian.my_qa.model.Image;

@Repository
public interface ImageDao extends JpaRepository<Image, Integer> {
    
}
