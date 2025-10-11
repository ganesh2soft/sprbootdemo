package com.hcl.sprbootdemo.exception;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	String resourceName;
	String field;
	String fieldName;
	Long fieldId;

	public ResourceNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResourceNotFoundException(String resourceName, String field, String fieldName) {
		super(String.format("%s not found with %s : %s", resourceName, field, fieldName));

		this.resourceName = resourceName;
		this.field = field;
		this.fieldName = fieldName;
	}

	public ResourceNotFoundException(String resourceName, String field, Long fieldId) {
		super(String.format("%s not found with %s : %d", resourceName, field, fieldId));
		this.resourceName = resourceName;
		this.field = field;
		this.fieldId = fieldId;
	}

	public ResourceNotFoundException(String resourceName, String field) {
		super(String.format("%s not found with %s ", resourceName, field));
		this.resourceName = resourceName;
		this.field = field;

	}

}
