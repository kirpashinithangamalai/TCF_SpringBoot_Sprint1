package com.tcf.studentresultanalytics.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.tcf.studentresultanalytics.dto.StudentAnalyticsDTO;
import com.tcf.studentresultanalytics.entity.StudentResult;
import com.tcf.studentresultanalytics.service.StudentResultService;

@RestController
@RequestMapping("/results")
public class StudentResultController {

    private final StudentResultService service;

    public StudentResultController(StudentResultService service) {
        this.service = service;
    }

    // 1. Insert result
    @PostMapping("/insertion")
    public StudentResult insertResult(
            @RequestBody StudentResult result) {

        return service.insertResult(result);
    }

    // 2. Get all results
    @GetMapping("/getallrecords")
    public List<StudentResult> getAllRecords() {

        return service.getAllRecords();
    }

    // 3. Get results by register number
    @GetMapping("/student/{registerNumber}")
    public List<StudentResult> getByRegisterNumber(
            @PathVariable String registerNumber) {

        return service.getByRegisterNumber(registerNumber);
    }

    // 4. Semester analytics
    @GetMapping("/analytics/{registerNumber}")
    public StudentAnalyticsDTO compareSemesters(
            @PathVariable String registerNumber,
            @RequestParam int previousSemester,
            @RequestParam int currentSemester) {

        return service.compareSemesters(
                registerNumber,
                previousSemester,
                currentSemester);
    }
    
 // 5. Update student result
    @PutMapping("/update/{id}")
    public StudentResult updateResult(
            @PathVariable Long id,
            @RequestBody StudentResult result) {

        return service.updateResult(id, result);
    }
    
 // 6. Delete student result
    @DeleteMapping("/delete/{id}")
    public String deleteResult(@PathVariable Long id) {

        service.deleteResult(id);

        return "Student result deleted successfully";
    }
}