package com.hcl.sprbootdemo.exception;

public class APIResponse {

	private String message;
	private boolean status;

	public APIResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public APIResponse(String message, boolean status) {
		super();
		this.message = message;
		this.status = status;
	}

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

	@Override
	public String toString() {
		return "APIResponse [message=" + message + ", status=" + status + "]";
	}

}
