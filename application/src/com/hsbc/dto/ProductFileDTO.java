package com.hsbc.dto;

public class ProductFileDTO {
	private int successCount;
	private int failedCount;
	
	public ProductFileDTO() {
		super();
	}
	
	public ProductFileDTO(int successCount, int failedCount) {
		super();
		this.successCount = successCount;
		this.failedCount = failedCount;
	}

	public int getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

	public int getFailedCount() {
		return failedCount;
	}

	public void setFailedCount(int failedCount) {
		this.failedCount = failedCount;
	}
		
}
