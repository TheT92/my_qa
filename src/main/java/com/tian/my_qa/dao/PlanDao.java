package com.tian.my_qa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tian.my_qa.model.StudyPlan;
import com.tian.my_qa.model.StudyPlanItem;

@Repository
public interface PlanDao extends JpaRepository<StudyPlan, Integer> {
    @Query(value = "SELECT * FROM study_plan q where q.title like %?1% and q.user_id = ?2 and q.del_flag != 1 limit ?3 offset ?4", nativeQuery = true)
    List<StudyPlan> getPlansPage(String query, Integer userId, Integer pageSize, Integer offset);

    @Query(value = "SELECT count(*) FROM study_plan q where q.title like %?1% and q.user_id = ?2 and q.del_flag != 1", nativeQuery = true)
    Integer getPlansPageNum(String query, Integer userId);

    @Query(value = "SELECT * FROM study_plan q where q.id = :id and q.user_id = :userId and q.del_flag != 1", nativeQuery = true)
    StudyPlan getPlanDetail(int id, int userId);
}
