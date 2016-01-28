package br.com.security.guardian;

import java.io.Serializable;

public class DefaultCredential implements Serializable, Credential {
	private static final long serialVersionUID = 3105918713474826266L;

	private String userName;
//	private Set<String> roles;
//	private Ser
	private boolean valid;
//	private SecurityUtil security;

	public DefaultCredential() {
		this.setAuthenticated(false);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isAuthenticated() {
		return valid;
	}

	public void setAuthenticated(boolean valid) {
		this.valid = valid;
	}
}
