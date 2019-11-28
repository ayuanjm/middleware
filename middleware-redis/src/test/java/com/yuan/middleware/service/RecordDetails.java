package com.yuan.middleware.service;

public class RecordDetails {
	
	private String  userId;
	private String loanProductNo;
	private String  productType;
	private String  userWelfare;
	private String  status;
	private String startTenderTime;
	private String endTenderTime;
	private String startExpirtTime;
	private String endExpirtTime;
	@Override
	public String toString() {
		return "RecordDetails [userId=" + userId + ", loanProductNo=" + loanProductNo + ", productType=" + productType
				+ ", userWelfare=" + userWelfare + ", status=" + status + ", startTenderTime=" + startTenderTime
				+ ", endTenderTime=" + endTenderTime + ", startExpirtTime=" + startExpirtTime + ", endExpirtTime="
				+ endExpirtTime + ", lendBeanMoney=" + lendBeanMoney + "]";
	}
	private String lendBeanMoney;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getLoanProductNo() {
		return loanProductNo;
	}
	public void setLoanProductNo(String loanProductNo) {
		this.loanProductNo = loanProductNo;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getUserWelfare() {
		return userWelfare;
	}
	public void setUserWelfare(String userWelfare) {
		this.userWelfare = userWelfare;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStartTenderTime() {
		return startTenderTime;
	}
	public void setStartTenderTime(String startTenderTime) {
		this.startTenderTime = startTenderTime;
	}
	public String getEndTenderTime() {
		return endTenderTime;
	}
	public void setEndTenderTime(String endTenderTime) {
		this.endTenderTime = endTenderTime;
	}
	public String getStartExpirtTime() {
		return startExpirtTime;
	}
	public void setStartExpirtTime(String startExpirtTime) {
		this.startExpirtTime = startExpirtTime;
	}
	public String getEndExpirtTime() {
		return endExpirtTime;
	}
	public void setEndExpirtTime(String endExpirtTime) {
		this.endExpirtTime = endExpirtTime;
	}
	public String getLendBeanMoney() {
		return lendBeanMoney;
	}
	public void setLendBeanMoney(String lendBeanMoney) {
		this.lendBeanMoney = lendBeanMoney;
	}
	public RecordDetails(String userId, String loanProductNo, String productType, String userWelfare, String status,
                         String startTenderTime, String endTenderTime, String startExpirtTime, String endExpirtTime,
                         String lendBeanMoney) {
		super();
		this.userId = userId;
		this.loanProductNo = loanProductNo;
		this.productType = productType;
		this.userWelfare = userWelfare;
		this.status = status;
		this.startTenderTime = startTenderTime;
		this.endTenderTime = endTenderTime;
		this.startExpirtTime = startExpirtTime;
		this.endExpirtTime = endExpirtTime;
		this.lendBeanMoney = lendBeanMoney;
	}
	public RecordDetails() {
	}

	
	

}
