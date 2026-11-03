package com.tcf.studentresultanalytics.dto;

public class SubjectPerformanceDTO {

    private String subject;
    private double previousMarks;
    private double currentMarks;
    private double marksDifference;
    private String performanceStatus;

    public SubjectPerformanceDTO() {
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public double getPreviousMarks() {
        return previousMarks;
    }

    public void setPreviousMarks(double previousMarks) {
        this.previousMarks = previousMarks;
    }

    public double getCurrentMarks() {
        return currentMarks;
    }

    public void setCurrentMarks(double currentMarks) {
        this.currentMarks = currentMarks;
    }

    public double getMarksDifference() {
        return marksDifference;
    }

    public void setMarksDifference(double marksDifference) {
        this.marksDifference = marksDifference;
    }

    public String getPerformanceStatus() {
        return performanceStatus;
    }

    public void setPerformanceStatus(String performanceStatus) {
        this.performanceStatus = performanceStatus;
    }
}