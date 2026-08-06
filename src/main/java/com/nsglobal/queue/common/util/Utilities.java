package com.nsglobal.queue.common.util;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;

public final class Utilities {
	
	

	public Utilities() {
		super();
	}
	
	public static boolean isLangFr() {
		Locale localCtx=LocaleContextHolder.getLocale();
		String lang=localCtx.getDisplayLanguage();
		String lang_code=localCtx.getLanguage();
		System.out.println("\n lang => %s \n lang_code => %s".formatted(lang,lang_code));
		return lang_code.toLowerCase()=="fr";
		}

}
