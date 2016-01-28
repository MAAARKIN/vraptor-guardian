package br.com.security.guardian;

public interface Credential {
	public void setUserName(String userName);
	public String getUserName();
	public boolean isAuthenticated();
	public void setAuthenticated(boolean valid);
}