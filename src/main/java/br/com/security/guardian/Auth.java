package br.com.security.guardian;

import java.util.List;
import java.util.Set;

public interface Auth {
	public Set<String> getRolesByUser(String username);
	public Set<String> getPermissionsByRole(String role);
	public boolean hasAuthorization(String userName, List<String> roles, List<String> permissions);
}
