package com.swpc.organicledger.util;

import java.text.MessageFormat;

public class MessageService {
	public static String getMessage(String template, Object[] args) throws Exception {
		MessageFormat formatter = new MessageFormat("");
		formatter.applyPattern(template);
		return formatter.format(args);
	}

}