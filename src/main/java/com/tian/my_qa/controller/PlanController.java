package com.tian.my_qa.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tian.my_qa.model.StudyPlan;
import com.tian.my_qa.model.StudyPlanItem;
import com.tian.my_qa.service.PlanService;

@RestController
@RequestMapping("plan")
public class PlanController {
    @Autowired
    PlanService ps;

    @GetMapping("plansPage")
    public ResponseEntity<Map<String, Object>> getPlansPage(@RequestParam String query, @RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestHeader(value = "token", required = true) String token) {
        return ps.getPlansPage(query, pageNum, pageSize, token);
    }

    @PostMapping("addPlan")
    public ResponseEntity<String> addPlan(@RequestBody StudyPlan plan, @RequestHeader(value = "token", required = true) String token) {
        return ps.addPlan(plan, token);
    }

    @GetMapping("{id}")
    public ResponseEntity<StudyPlan> getPlan(@PathVariable int id, @RequestHeader(value = "token", required = true) String token) {
        return ps.findById(id, token);
    }

    @PostMapping("editPlan")
    public ResponseEntity<String> editPlan(@RequestBody StudyPlan plan) {
        return ps.editPlan(plan);
    }

    @PostMapping("addItem")
    public ResponseEntity<String> addItem(@RequestBody StudyPlanItem item, @RequestHeader(value = "token", required = true) String token) {
        return ps.addItem(item, token);
    }

    @GetMapping("planItems")
    public ResponseEntity<Map<String, Object>> getPlanItems(@RequestParam int planId, @RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestHeader(value = "token", required = true) String token) {
        return ps.getPlanItems(planId, pageNum, pageSize, token);
    }
}
