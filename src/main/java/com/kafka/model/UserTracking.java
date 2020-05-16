package com.kafka.model;

import org.apache.catalina.Session;
import org.springframework.stereotype.Component;

@Component
public class UserTracking {

	private String sessionID;
	private String customerID;
	private String activityList;
	public UserTracking() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserTracking(String sessionID, String customerID, String activityList) {
		super();
		this.sessionID = sessionID;
		this.customerID = customerID;
		this.activityList = activityList;
	}
	public String getSessionID() {
		return sessionID;
	}
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	public String getActivityList() {
		return activityList;
	}
	public void setActivityList(String activityList) {
		this.activityList = activityList;
	}
	@Override
	public String toString() {
		return "UserTracking [sessionID=" + sessionID + ", customerID=" + customerID + ", activityList=" + activityList
				+ "]";
	}	
	
	
}
