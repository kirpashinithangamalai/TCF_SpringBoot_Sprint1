package com.tcf.studentresultanalytics.service;

import java.util.List;

import com.tcf.studentresultanalytics.dto.StudentAnalyticsDTO;
import com.tcf.studentresultanalytics.entity.StudentResult;

public interface StudentResultService {

    // Insert result
    StudentResult insertResult(StudentResult result);

    // Get all records
    List<StudentResult> getAllRecords();

    // Get records by register number
    List<StudentResult> getByRegisterNumber(String registerNumber);

    // Get record by database ID
    StudentResult getById(Long id);

    // Compare previous and current semester
    StudentAnalyticsDTO compareSemesters(
            String registerNumber,
            int previousSemester,
            int currentSemester);

    // Update result
    StudentResult updateResult(Long id, StudentResult result);

    // Delete result
    void deleteResult(Long id);
}