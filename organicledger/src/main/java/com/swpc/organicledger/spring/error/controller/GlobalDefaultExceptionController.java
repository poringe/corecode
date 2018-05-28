package com.swpc.organicledger.spring.error.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.swpc.organicledger.exception.OrganicLedgerServiceException;
import com.swpc.organicledger.spring.error.model.ApiError;
import com.swpc.organicledger.util.MessageService;

@ControllerAdvice

public class GlobalDefaultExceptionController {

	@Resource(name = "messageSource")
	private MessageSource messageSource;

	@ExceptionHandler({ MethodArgumentNotValidException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			WebRequest request) {
		ex.printStackTrace();
		List<String> errors = new ArrayList<String>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, errors);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@ExceptionHandler({ OrganicLedgerServiceException.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ResponseEntity<Object> handleOrganicLedgerService(OrganicLedgerServiceException ex, WebRequest request)
			throws Exception {
		ex.printStackTrace();
		String localeStr = request.getHeader("Accept-Language");
		Locale locale;
		try {
			locale = new Locale(localeStr);
		} catch (Exception e) {
			locale = Locale.US;
		}
		ResourceBundle labels = ResourceBundle.getBundle("ErrorMessages", locale);
		Object[] args = { ex.getField() };
		String errorMessage = MessageService.getMessage(labels.getString(ex.getMessage()), args);
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@ExceptionHandler({ Exception.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) throws Exception {
		ex.printStackTrace();
		String localeStr = request.getHeader("Accept-Language");
		Locale locale;
		try {
			locale = new Locale(localeStr);
		} catch (Exception e) {
			locale = Locale.US;
		}
		ResourceBundle labels = ResourceBundle.getBundle("ErrorMessages", locale);
		Object[] args = {};
		String errorMessage = MessageService.getMessage(labels.getString("unknown_error"), args);
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
}