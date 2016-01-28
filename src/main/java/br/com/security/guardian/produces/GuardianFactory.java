package br.com.security.guardian.produces;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.security.guardian.Credential;
import br.com.security.guardian.DefaultCredential;
import br.com.security.guardian.annotation.Credentials;

@ApplicationScoped
public class GuardianFactory {
	
	private static final Logger log = LoggerFactory.getLogger(GuardianFactory.class);

	@PostConstruct
	public void init() {
		System.out.println("teste");
		log.info("Initializing Guardian");
	}
	
	@Produces @SessionScoped @Credentials
	public Credential getUser() {
		return new DefaultCredential();
	}
	
//	@Produces
//	public SecurityUtil getSecurity() {
//		System.out.println("teste");
//		return new SecurityUtil();
//	}
}
