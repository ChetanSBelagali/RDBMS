package com.aroha.RDBMS.payload;

import java.util.List;

public class DataResponse {

	private boolean status;
	private String message;
	private List result;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public List getResult() {
		return result;
	}

	public void setResult(List result) {
		this.result = result;
	}



}
