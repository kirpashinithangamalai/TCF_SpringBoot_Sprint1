package com.tcf.studentresultanalytics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcf.studentresultanalytics.entity.StudentResult;

public interface StudentResultRepository
        extends JpaRepository<StudentResult, Long> {

    List<StudentResult> findByRegisterNumber(String registerNumber);

    List<StudentResult> findByRegisterNumberAndSemester(
            String registerNumber, int semester);
}