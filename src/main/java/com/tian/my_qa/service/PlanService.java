package com.tian.my_qa.service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tian.my_qa.dao.PlanDao;
import com.tian.my_qa.dao.PlanItemDao;
import com.tian.my_qa.model.StudyPlan;
import com.tian.my_qa.model.StudyPlanItem;
import com.tian.my_qa.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;

@Service
public class PlanService {
    @Autowired
    PlanDao pd;

    @Autowired
    PlanItemDao pid;

    // 查询用户的所有学习计划
    public ResponseEntity<Map<String, Object>> getPlansPage(String query, Integer pageNum, Integer pageSize,
            String token) {
        try {
            String userId = JwtUtil.getMemberIdByJwtToken(token);
            Map<String, Object> res = new HashMap<>();
            Integer offset = (pageNum > 1 ? pageNum - 1 : 0) * pageSize;
            List<StudyPlan> list = pd.getPlansPage(query, Integer.parseInt(userId), pageSize, offset);
            Integer total = pd.getPlansPageNum(query, Integer.parseInt(userId));
            res.put("list", list);
            res.put("total", total);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> addPlan(StudyPlan plan, String token) {
        try {
            String id = JwtUtil.getMemberIdByJwtToken(token);
            plan.setUserId(Integer.parseInt(id));
            String now = new Date().toString();
            plan.setCreatedTime(now);
            pd.save(plan);
            return new ResponseEntity<>("success", HttpStatus.CREATED);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>("fail", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
        }
    }

    // 查询计划详情
    public ResponseEntity<StudyPlan> findById(int id, String token) {
        try {
            String userId = JwtUtil.getMemberIdByJwtToken(token);
            StudyPlan plan = pd.getPlanDetail(id, Integer.parseInt(userId));
            return new ResponseEntity<>(plan, HttpStatus.OK);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }  catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> editPlan(StudyPlan plan) {
        try {
            pd.save(plan);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
        }
    }

    // 查询计划下的所有计划项
    public ResponseEntity<Map<String, Object>> getPlanItems(int planId, Integer pageNum, Integer pageSize, String token) {
        try {
            String userId = JwtUtil.getMemberIdByJwtToken(token);
            Map<String, Object> res = new HashMap<>();
            Integer offset = (pageNum > 1 ? pageNum - 1 : 0) * pageSize;
            List<StudyPlanItem> list = pid.getPlanItemsPage(planId, pageSize, offset);
            System.out.println(list.toString());
            Integer total = pid.getPlanItemsNum(planId, Integer.parseInt(userId));
            res.put("list", list);
            res.put("total", total);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }  catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> addItem(StudyPlanItem item, String token) {
        try {
            String id = JwtUtil.getMemberIdByJwtToken(token);
            String now = new Date().toString();
            item.setCreatedTime(now);
            System.out.println(item.toString());
            pid.save(item);
            return new ResponseEntity<>("success", HttpStatus.CREATED);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>("fail", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
        }
    }
}
