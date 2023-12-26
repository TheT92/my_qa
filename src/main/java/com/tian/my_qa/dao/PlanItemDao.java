package com.tian.my_qa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tian.my_qa.model.StudyPlanItem;

@Repository
public interface PlanItemDao extends JpaRepository<StudyPlanItem, Integer> {
    @Query(value = "SELECT * FROM study_plan_item q where q.plan_id = :planId and q.del_flag != 1 limit :pageSize offset :offset", nativeQuery = true)
    List<StudyPlanItem> getPlanItemsPage(int planId, Integer pageSize, Integer offset);

    @Query(value = "SELECT count(*) FROM study_plan_item q where q.plan_id = ?1 and q.del_flag != 1", nativeQuery = true)
    Integer getPlanItemsNum(int planId, int userId);
}
