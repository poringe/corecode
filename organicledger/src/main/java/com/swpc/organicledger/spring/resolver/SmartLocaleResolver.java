package com.swpc.organicledger.spring.resolver;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

public class SmartLocaleResolver extends AcceptHeaderLocaleResolver {

	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		String acceptLanguage = request.getHeader("Accept-Language");
		if (acceptLanguage == null || acceptLanguage.trim().isEmpty()) {
			return Locale.US;
		}
		try {
			return new Locale(acceptLanguage);
		} catch (Exception e) {
			return Locale.US;
		}
	}

}