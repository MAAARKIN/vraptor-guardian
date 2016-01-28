package br.com.security.guardian.util;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

public class SecurityUtil {

	@Inject
	private HttpSession session;
	
	public SecurityUtil() {
		
	}
	
	public void invalidate() {
		session.invalidate();
	}
}
