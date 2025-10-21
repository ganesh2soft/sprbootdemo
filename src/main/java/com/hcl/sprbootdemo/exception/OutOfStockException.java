package com.hcl.sprbootdemo.exception;

public class OutOfStockException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	String resourceName;
	int requestedQuantity;
	int availableQuantity;

	public OutOfStockException() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public OutOfStockException(String resourceName, int requestedQuantity, int availableQuantity) {
        super(String.format(
            "Product '%s' is out of stock. Requested: %d, Available: %d",
            resourceName, requestedQuantity, availableQuantity
        ));
        this.resourceName = resourceName;
        this.requestedQuantity = requestedQuantity;
        this.availableQuantity = availableQuantity;
    }

}
