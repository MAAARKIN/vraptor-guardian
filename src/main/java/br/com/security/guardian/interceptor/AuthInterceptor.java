package br.com.security.guardian.interceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.Accepts;
import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.com.security.guardian.Auth;
import br.com.security.guardian.Credential;
import br.com.security.guardian.annotation.GuardController;
import br.com.security.guardian.annotation.GuardMethod;
import br.com.security.guardian.listener.RestrictionListener;

@Intercepts
public class AuthInterceptor {

	private static final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);

	@Inject private Credential credential;
	@Inject private Auth authService;
	@Inject private RestrictionListener listener;
	@Inject private ControllerMethod method;

	@Accepts
	public boolean accepts(ControllerMethod method) {
		return (isGuardMethodPresent(method) || isGuardClassPresent(method));
	}

	@AroundCall
	public void intercept(SimpleInterceptorStack stack) {
		log.info("Credential: " + credential.isAuthenticated());

		if (!credential.isAuthenticated()) {
			listener.redirect();
		} else {
			String[] roles = null;
			String[] permissions = null;

			GuardController guardClass = (isGuardClassPresent(method) ? method.getController().getType().getAnnotation(GuardController.class) : null);
			GuardMethod guardMethod = (isGuardMethodPresent(method) ? method.getMethod().getAnnotation(GuardMethod.class) : null);

			if (guardClass != null && guardClass.roles() != null) {
				roles = guardClass.roles();
			}

			if (guardMethod != null) {
				if ((guardClass == null || guardClass.roles() == null) && guardMethod.roles() != null) {
					roles = guardMethod.roles();
				}

				if (guardMethod.permissions() != null) {
					permissions = guardMethod.permissions();
				}
			}

			if (hasAccess(roles, permissions, credential.getUserName())) {
				stack.next();
			} else {
				listener.redirect();
			}
		}
	}

	private boolean hasAccess(String[] roles, String[] permissions, String userName) {
		// this situations will happen when the developer just need a credential
		// authenticated
		if (roles == null && permissions == null) {
			return true;
		}

		// the developer will receive a empty set or the set list to use, he
		// don't need validate the nullability from permissions and roles
		List<String> rolesCollection = (roles != null && roles.length > 0 ? Arrays.asList(roles) : new ArrayList<String>());
		List<String> permissionsCollection = (permissions != null && permissions.length > 0 ? Arrays.asList(permissions) : new ArrayList<String>());

		// the developer validate as he want
		return authService.hasAuthorization(userName, rolesCollection, permissionsCollection);
	}

	private boolean isGuardClassPresent(ControllerMethod method) {
		return method.getController().getType().isAnnotationPresent(GuardController.class);
	}

	private boolean isGuardMethodPresent(ControllerMethod method) {
		return method.getMethod().isAnnotationPresent(GuardMethod.class);
	}
}
