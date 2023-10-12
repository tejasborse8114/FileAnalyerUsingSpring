package com.example.demo;

import java.util.Date;


public class EmployeeRecord {
    private String positionID;
    private String positionStatus;
    private Date time; // Updated field name
    private Date timeOut;
    private String timecardHours;
    private Date payCycleStartDate;
    private Date payCycleEndDate;
    private String employeeName;
    private String fileNumber;

    public EmployeeRecord(String positionID, String positionStatus, Date time, Date timeOut, String timecardHours, Date payCycleStartDate, Date payCycleEndDate, String employeeName, String fileNumber) {
        this.positionID = positionID;
        this.positionStatus = positionStatus;
        this.time = time;
        this.timeOut = timeOut;
        this.timecardHours = timecardHours;
        this.payCycleStartDate = payCycleStartDate;
        this.payCycleEndDate = payCycleEndDate;
        this.employeeName = employeeName;
        this.fileNumber = fileNumber;
    }

	public String getPositionID() {
		return positionID;
	}

	public void setPositionID(String positionID) {
		this.positionID = positionID;
	}

	public String getPositionStatus() {
		return positionStatus;
	}

	public void setPositionStatus(String positionStatus) {
		this.positionStatus = positionStatus;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Date getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(Date timeOut) {
		this.timeOut = timeOut;
	}

	public String getTimecardHours() {
		return timecardHours;
	}

	public void setTimecardHours(String timecardHours) {
		this.timecardHours = timecardHours;
	}

	public Date getPayCycleStartDate() {
		return payCycleStartDate;
	}

	public void setPayCycleStartDate(Date payCycleStartDate) {
		this.payCycleStartDate = payCycleStartDate;
	}

	public Date getPayCycleEndDate() {
		return payCycleEndDate;
	}

	public void setPayCycleEndDate(Date payCycleEndDate) {
		this.payCycleEndDate = payCycleEndDate;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getFileNumber() {
		return fileNumber;
	}

	public void setFileNumber(String fileNumber) {
		this.fileNumber = fileNumber;
	}

    // Getters and setters for the fields...
}

