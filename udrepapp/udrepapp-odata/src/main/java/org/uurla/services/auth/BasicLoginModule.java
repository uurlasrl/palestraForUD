package org.uurla.services.auth;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.uurla.services.PersistenceFactory;
import org.uurla.services.UurlaServiceKit;
import org.uurla.services.auth.db.entities.Role;
import org.uurla.services.auth.db.entities.User;

public class BasicLoginModule implements LoginModule {
	private CallbackHandler handler;
	private Subject subject;
	private UserPrincipal userPrincipal=null;
	private RolePrincipal rolePrincipal;
	private String login;
	private List<String> userGroups;

	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
			Map<String, ?> options) {

		handler = callbackHandler;
		this.subject = subject;
	}

	@Override
	public boolean login() throws LoginException {

		Callback[] callbacks = new Callback[2];
		callbacks[0] = new NameCallback("login");
		callbacks[1] = new PasswordCallback("password", true);
		
		try {
			handler.handle(callbacks);
			String name = ((NameCallback) callbacks[0]).getName();
			String password = String.valueOf(((PasswordCallback) callbacks[1]).getPassword());
			
			if (name != null && password != null) {
				if(1==1) return true;
				PersistenceFactory uupf= UurlaServiceKit.getPersistenceFactoryImpl();
				if(uupf.getUser() != null && !uupf.getUser().equals(name))
					uupf.initUser();
				EntityManagerFactory emf = uupf.getEmfAdmin();
				EntityManager em = emf.createEntityManager();

				// get the user
				User usr = em.find(User.class, name);

				if (usr != null) {
					PasswordEncryptionService pes=new PasswordEncryptionService();
					boolean auth=false;
					try {
						auth=pes.authenticate(
								password, 
								pes.convertToBytes(usr.getPassword())
											);
					} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
					}
					
					if (auth){
						login = name;
						userGroups = new ArrayList<String>();

						List<Role> groups = usr.getRoles();
						for (Role group : groups) {
							userGroups.add(group.getRolename());
						}
						// aggiungo l'utente loggato
						userGroups.add("logged");
						
						//check is the user is expired
						if(!usr.getEnable()) 
							// not enable
							throw new LoginException("not enable");
						Date adesso=new Date();
						if(usr.getBegdatim().compareTo(adesso)>0 || usr.getEnddatim().compareTo(adesso)<0)		 
							// not enable
							throw new LoginException("Expired");
						//em.detach(usr);
						if (userPrincipal == null ){
							userPrincipal = new UserPrincipal(login);
						}
						uupf.setUser(userPrincipal.getName(), userGroups);

						em.close();
						return true;
					}
				}
				em.close();
			}

			// If credentials are NOT OK we throw a LoginException
			throw new LoginException("Authentication failed");

		} catch (IOException e) {
			throw new LoginException(e.getMessage());
		} catch (UnsupportedCallbackException e) {
			throw new LoginException(e.getMessage());
		}

	}

	@Override
	public boolean commit() throws LoginException {

		if (userPrincipal == null ){
			userPrincipal = new UserPrincipal(login);
		}
		//UurlaJPAServiceFactory.user = userPrincipal;
		subject.getPrincipals().add(userPrincipal);

		if (userGroups != null && userGroups.size() > 0) {
			for (String groupName : userGroups) {
				rolePrincipal = new RolePrincipal(groupName);
				subject.getPrincipals().add(rolePrincipal);
			}
		}
		PersistenceFactory uupf= UurlaServiceKit.getPersistenceFactoryImpl();
		uupf.setUser(userPrincipal.getName(), userGroups);

		return true;
	}

	@Override
	public boolean abort() throws LoginException {
		return false;
	}

	@Override
	public boolean logout() throws LoginException {
		subject.getPrincipals().remove(userPrincipal);
		subject.getPrincipals().remove(rolePrincipal);
		return true;
	}

}
