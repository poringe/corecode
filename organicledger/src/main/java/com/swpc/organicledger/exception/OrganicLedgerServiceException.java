package com.swpc.organicledger.exception;

public class OrganicLedgerServiceException extends Exception {

	private String field;

	public OrganicLedgerServiceException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrganicLedgerServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public OrganicLedgerServiceException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public OrganicLedgerServiceException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	public OrganicLedgerServiceException(String message, String field) {
		super(message);
		this.setField(field);
		// TODO Auto-generated constructor stub
	}

	public OrganicLedgerServiceException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

}
