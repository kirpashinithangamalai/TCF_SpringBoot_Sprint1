
package com.tcf.studentresultanalytics.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tcf.studentresultanalytics.dto.StudentAnalyticsDTO;
import com.tcf.studentresultanalytics.dto.SubjectPerformanceDTO;
import com.tcf.studentresultanalytics.entity.StudentResult;
import com.tcf.studentresultanalytics.exception.StudentResultNotFoundException;
import com.tcf.studentresultanalytics.repository.StudentResultRepository;
import com.tcf.studentresultanalytics.service.StudentResultService;

@Service
public class StudentResultServiceImpl implements StudentResultService {

    private final StudentResultRepository repository;

    public StudentResultServiceImpl(
            StudentResultRepository repository) {
        this.repository = repository;
    }

    // 1. Insert student result
    @Override
    public StudentResult insertResult(StudentResult result) {

        validateResult(result);

        return repository.save(result);
    }

    // 2. Get all student results
    @Override
    public List<StudentResult> getAllRecords() {

        return repository.findAll();
    }

    // 3. Get results by register number
    @Override
    public List<StudentResult> getByRegisterNumber(
            String registerNumber) {

        List<StudentResult> results =
                repository.findByRegisterNumber(registerNumber);

        if (results.isEmpty()) {
            throw new StudentResultNotFoundException(
                    "Student result not found for Register Number: "
                            + registerNumber);
        }

        return results;
    }

    // 4. Get student result by database ID
    @Override
    public StudentResult getById(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new StudentResultNotFoundException(
                                "Student result not found with ID: " + id));
    }

    // 5. Compare previous and current semester
    @Override
    public StudentAnalyticsDTO compareSemesters(
            String registerNumber,
            int previousSemester,
            int currentSemester) {

        if (registerNumber == null || registerNumber.isBlank()) {
            throw new IllegalArgumentException(
                    "Register Number is required");
        }

        if (previousSemester <= 0 || currentSemester <= 0) {
            throw new IllegalArgumentException(
                    "Semester numbers must be positive");
        }

        if (previousSemester == currentSemester) {
            throw new IllegalArgumentException(
                    "Previous and current semester must be different");
        }

        // Fetch previous semester results
        List<StudentResult> previousResults =
                repository.findByRegisterNumberAndSemester(
                        registerNumber, previousSemester);

        // Fetch current semester results
        List<StudentResult> currentResults =
                repository.findByRegisterNumberAndSemester(
                        registerNumber, currentSemester);

        if (previousResults.isEmpty() || currentResults.isEmpty()) {
            throw new StudentResultNotFoundException(
                    "Results not found for the selected semesters");
        }

        // Calculate previous semester totals
        double previousMarks = 0;
        double previousTotal = 0;

        for (StudentResult result : previousResults) {
            previousMarks += result.getMarks();
            previousTotal += result.getTotalMarks();
        }

        // Calculate current semester totals
        double currentMarks = 0;
        double currentTotal = 0;

        for (StudentResult result : currentResults) {
            currentMarks += result.getMarks();
            currentTotal += result.getTotalMarks();
        }

        // Calculate percentages
        double previousPercentage =
                (previousMarks / previousTotal) * 100;

        double currentPercentage =
                (currentMarks / currentTotal) * 100;

        // Calculate differences
        double marksDifference =
                currentMarks - previousMarks;

        double percentageDifference =
                currentPercentage - previousPercentage;

        // Overall performance status
        String overallStatus;

        if (percentageDifference > 0) {
            overallStatus = "IMPROVED";
        } else if (percentageDifference < 0) {
            overallStatus = "DECREMENT";
        } else {
            overallStatus = "MAINTAINED";
        }

        // Pass / Fail (50% pass threshold)
        String resultStatus =
                currentPercentage >= 50 ? "PASS" : "FAIL";

        // Store previous semester subjects
        Map<String, StudentResult> previousSubjectMap =
                new HashMap<>();

        for (StudentResult result : previousResults) {

            String subjectKey =
                    result.getSubject().trim().toLowerCase();

            previousSubjectMap.put(subjectKey, result);
        }

        // Subject-wise comparison
        List<SubjectPerformanceDTO> subjectList =
                new ArrayList<>();

        String weakSubject = null;
        double lowestPercentage = Double.MAX_VALUE;

        for (StudentResult current : currentResults) {

            String subjectKey =
                    current.getSubject().trim().toLowerCase();

            StudentResult previous =
                    previousSubjectMap.get(subjectKey);

            // Skip subjects not available in previous semester
            if (previous == null) {
                continue;
            }

            // Calculate subject marks difference
            double difference =
                    current.getMarks() - previous.getMarks();

            String subjectStatus;

            if (difference > 0) {
                subjectStatus = "IMPROVED";
            } else if (difference < 0) {
                subjectStatus = "DECREMENT";
            } else {
                subjectStatus = "MAINTAINED";
            }

            // Create subject DTO
            SubjectPerformanceDTO subjectDTO =
                    new SubjectPerformanceDTO();

            subjectDTO.setSubject(current.getSubject());
            subjectDTO.setPreviousMarks(previous.getMarks());
            subjectDTO.setCurrentMarks(current.getMarks());
            subjectDTO.setMarksDifference(difference);
            subjectDTO.setPerformanceStatus(subjectStatus);

            subjectList.add(subjectDTO);

            // Identify weakest current subject
            double subjectPercentage =
                    (current.getMarks() / current.getTotalMarks()) * 100;

            if (subjectPercentage < lowestPercentage) {

                lowestPercentage = subjectPercentage;
                weakSubject = current.getSubject();
            }
        }

        // Create analytics DTO
        StudentAnalyticsDTO dto = new StudentAnalyticsDTO();

        dto.setRegisterNumber(registerNumber);
        dto.setStudentName(
                currentResults.get(0).getStudentName());

        dto.setPreviousSemester(previousSemester);
        dto.setCurrentSemester(currentSemester);

        dto.setPreviousTotalMarks(previousMarks);
        dto.setCurrentTotalMarks(currentMarks);

        dto.setPreviousPercentage(previousPercentage);
        dto.setCurrentPercentage(currentPercentage);

        dto.setMarksImprovement(marksDifference);
        dto.setPercentageImprovement(percentageDifference);

        dto.setPerformanceStatus(overallStatus);
        dto.setResultStatus(resultStatus);

        dto.setWeakSubject(weakSubject);
        dto.setSubjectPerformance(subjectList);

        return dto;
    }

    // 6. Update student result
    @Override
    public StudentResult updateResult(
            Long id, StudentResult result) {

        // Check whether result exists
        StudentResult existingResult = getById(id);

        // Validate updated data
        validateResult(result);

        // Update fields
        existingResult.setRegisterNumber(
                result.getRegisterNumber());

        existingResult.setStudentName(
                result.getStudentName());

        existingResult.setDepartment(
                result.getDepartment());

        existingResult.setSemester(
                result.getSemester());

        existingResult.setSubject(
                result.getSubject());

        existingResult.setMarks(
                result.getMarks());

        existingResult.setTotalMarks(
                result.getTotalMarks());

        return repository.save(existingResult);
    }

    // 7. Delete student result
    @Override
    public void deleteResult(Long id) {

        StudentResult existingResult = getById(id);

        repository.delete(existingResult);
    }

    // 8. Common validation method
    private void validateResult(StudentResult result) {

        if (result == null) {
            throw new IllegalArgumentException(
                    "Student result cannot be empty");
        }

        if (result.getRegisterNumber() == null
                || result.getRegisterNumber().isBlank()) {

            throw new IllegalArgumentException(
                    "Register Number is required");
        }

        if (result.getStudentName() == null
                || result.getStudentName().isBlank()) {

            throw new IllegalArgumentException(
                    "Student name is required");
        }

        if (result.getSemester() <= 0) {

            throw new IllegalArgumentException(
                    "Semester must be positive");
        }

        if (result.getSubject() == null
                || result.getSubject().isBlank()) {

            throw new IllegalArgumentException(
                    "Subject is required");
        }

        if (result.getMarks() < 0
                || result.getTotalMarks() <= 0
                || result.getMarks() > result.getTotalMarks()) {

            throw new IllegalArgumentException(
                    "Invalid marks or total marks");
        }
    }
}
