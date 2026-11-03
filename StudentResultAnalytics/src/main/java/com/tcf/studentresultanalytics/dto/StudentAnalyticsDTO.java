package com.tcf.studentresultanalytics.dto;

import java.util.List;

public class StudentAnalyticsDTO {

    private Long studentId;
    private String studentName;

    private int previousSemester;
    private int currentSemester;

    private double previousTotalMarks;
    private double currentTotalMarks;

    private double previousPercentage;
    private double currentPercentage;

    private double marksImprovement;
    private double percentageImprovement;

    private String performanceStatus;
    private String resultStatus;

    private String weakSubject;

    private List<SubjectPerformanceDTO> subjectPerformance;

    public StudentAnalyticsDTO() {
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getPreviousSemester() {
        return previousSemester;
    }

    public void setPreviousSemester(int previousSemester) {
        this.previousSemester = previousSemester;
    }

    public int getCurrentSemester() {
        return currentSemester;
    }

    public void setCurrentSemester(int currentSemester) {
        this.currentSemester = currentSemester;
    }

    public double getPreviousTotalMarks() {
        return previousTotalMarks;
    }

    public void setPreviousTotalMarks(double previousTotalMarks) {
        this.previousTotalMarks = previousTotalMarks;
    }

    public double getCurrentTotalMarks() {
        return currentTotalMarks;
    }

    public void setCurrentTotalMarks(double currentTotalMarks) {
        this.currentTotalMarks = currentTotalMarks;
    }

    public double getPreviousPercentage() {
        return previousPercentage;
    }

    public void setPreviousPercentage(double previousPercentage) {
        this.previousPercentage = previousPercentage;
    }

    public double getCurrentPercentage() {
        return currentPercentage;
    }

    public void setCurrentPercentage(double currentPercentage) {
        this.currentPercentage = currentPercentage;
    }

    public double getMarksImprovement() {
        return marksImprovement;
    }

    public void setMarksImprovement(double marksImprovement) {
        this.marksImprovement = marksImprovement;
    }

    public double getPercentageImprovement() {
        return percentageImprovement;
    }

    public void setPercentageImprovement(double percentageImprovement) {
        this.percentageImprovement = percentageImprovement;
    }

    public String getPerformanceStatus() {
        return performanceStatus;
    }

    public void setPerformanceStatus(String performanceStatus) {
        this.performanceStatus = performanceStatus;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getWeakSubject() {
        return weakSubject;
    }

    public void setWeakSubject(String weakSubject) {
        this.weakSubject = weakSubject;
    }

    public List<SubjectPerformanceDTO> getSubjectPerformance() {
        return subjectPerformance;
    }

    public void setSubjectPerformance(
            List<SubjectPerformanceDTO> subjectPerformance) {
        this.subjectPerformance = subjectPerformance;
    }

	public void setRegisterNumber(String registerNumber) {
		// TODO Auto-generated method stub
		
	}
}