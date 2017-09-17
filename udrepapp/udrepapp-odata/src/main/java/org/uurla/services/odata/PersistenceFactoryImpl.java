package org.uurla.services.odata;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;


import org.uurla.services.auth.db.entities.Role;
import org.uurla.services.auth.db.entities.User;
import org.uurla.services.db.entities.AppParameter;
import org.uurla.services.PersistenceFactory;
import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;

/*
 *  si tratta dell'oggetto che conserva lo stato dell'applicazione
 *  contiene: l'utente, la connessione al db e anche il contesto delle chiamate odata.
 *  initDBData
 *  In fase di inizializzazione questo singleton controlla se il DB esiste e se non esiste lo crea
 *  Inserendo dei dati di default.
 *  
 */
public class PersistenceFactoryImpl implements PersistenceFactory {

	/*
	 * ogni applicazione ha due tipologie di persistence unit name che dal punto
	 * di vista delle tabelle a cui fanno riferimento sono identiche
	 * 
	 */
	static String PUNIT_NAME = "org.uurla.projects.db.persistence";
	static String PUNIT_NAME_ADMIN = "org.uurla.projects.db.persistence.admin";

	EntityManagerFactory emf = null;
	EntityManagerFactory emfAdmin = null;

	String login = null;
	String appName = null;
	List<String> userGroups = new ArrayList<String>();
	Locale containerLocale = null;
	static PersistenceFactoryImpl internalInstance = null;
	ResourceBundle bundle = null;
	ODataJPAContext JPAContext = null;
	ODataContext oDataContext = null;
	User user = null;


	public void setOdataContext(Object context) {
		oDataContext = (ODataContext) context;
	}

	public Object getOdataContext() {
		return oDataContext;
	}

	public void initUser() {
		login = null;
		appName = null;
		userGroups = new ArrayList<String>();
		containerLocale = null;
		bundle = null;
		JPAContext = null;
		user = null;
	}

	@Override
	public EntityManagerFactory getEmf() {
		return emf;
	}

	@Override
	public void setODataJPAContext(Object obj) {
		JPAContext = (ODataJPAContext) obj;
	}

	@Override
	public Object getODataJPAContext() {
		return JPAContext;
	}

	

	
	/*
	 * controlla se l'utente fa parte del ruolo passato 
	 * @see org.uurla.services.PersistenceFactory#isRole(java.lang.String)
	 */
	public boolean isRole(String role){
//		if(roles==null) {
		  EntityManager em = emfAdmin.createEntityManager();
		  Query qr=em.createQuery("SELECT A1 FROM  Role A1 JOIN A1.users B1 WHERE A1.rolename = :rname AND B1.username = :uname");
		  qr.setParameter("rname", role);
		  qr.setParameter("uname", this.getUser());
		  //User uu=null;
		  //Role rl=null;
		  //rl.getU
		  boolean rr = qr.getSingleResult()!=null;
		  
		  em.close();
		  return rr;
		  
		  
/*		  String us=this.getUser();
		  User usrObj=em.find(User.class, us);
		  roles=new TreeSet<String>();
		  List<AuthGroup> laug= usrObj.getAuthgroups();
		  for(AuthGroup aug : laug){
			  String ts=aug.getGroupName();
			  roles.add(ts);
		  }
		}
		return roles.contains(role);*/
	}



	private static void addProcedureForApp(EntityManager em, String appname, String[] procedures, String value) {
		for (int i = 0; i < procedures.length; i++)
			em.persist(new AppParameter(appname + "-" + procedures[i], value));
	}



	public PersistenceFactory getInstance() {
		if (internalInstance == null) {
			try {
				internalInstance = new PersistenceFactoryImpl();
				internalInstance.emf = Persistence.createEntityManagerFactory(PUNIT_NAME);
				internalInstance.emfAdmin = Persistence.createEntityManagerFactory(PUNIT_NAME_ADMIN);
			} catch (Exception e) {
				Logger log = Logger.getLogger(PersistenceFactoryImpl.class.getName());
				log.log(Level.SEVERE, "DB instantiation error", e);
				// e.printStackTrace();
			}
		}
		return internalInstance;
	}

	@Override
	public String getPersistenceUnitName() {
		return PUNIT_NAME;
	}

	public PersistenceFactoryImpl() {
	}

	@Override
	public void clearUser() {
		this.login = null;
		this.userGroups = new ArrayList<String>();
		;
	}

	@Override
	public void setUser(String login, List<String> groups) {
		this.login = login;
		if (groups != null)
			this.userGroups.addAll(groups);
	}

	@Override
	public String getUser() {
		return this.login;
	}

	@Override
	public boolean isInGroup(String group) {
		return userGroups.contains(group);
	}

	@Override
	public List<String> getGroups() {
		return userGroups;
	}

	@Override
	public EntityManagerFactory getEmfAdmin() {
		return this.emfAdmin;
	}

	@Override
	public String getPersistenceUnitNameAdmin() {
		return PUNIT_NAME_ADMIN;
	}

	@Override
	public void setAppName(String appName) {
		this.appName = appName;

	}

	@Override
	public String getAppName() {
		return this.appName;

	}

	@Override
	public void setContainerLocale(Locale l) {
		this.containerLocale = l;
	}


	Logger getLogger() {
		return Logger.getLogger(this.getClass().getName());
	}

	private Properties parametri = new Properties();

	public String getAppParameterValue(String name, boolean bypassbuffer) {
		if (name == null)
			return null;
		String prp = null;
		if (!bypassbuffer)
			prp = parametri.getProperty(name);
		if (prp == null) {
			EntityManager em = emfAdmin.createEntityManager();
			try {
				AppParameter ap = em.find(AppParameter.class, name);
				if (ap != null)
					parametri.setProperty(name, prp = ap.getValue());
			} finally {
				if (em != null)
					em.close();
			}
		}
		return prp;
	}
	Edm edm=null;
	@Override
	public void setEntityDataModel(Object edmobj){
		this.edm=(Edm)edmobj;
	}
	@Override
	public Object getEntityDataModel(){
		return (Object)this.edm;
	}

	String realPath=null;
	@Override
	public void setRealPath(String str) {
	  realPath = str;
	}

	@Override
	public String getRealPath() {
		if(realPath==null){
			EntityManager em = this.getEmf().createEntityManager();			
			AppParameter ap=em.find(AppParameter.class, "RealPath");
			AppParameter ap1=em.find(AppParameter.class, "RealPath1");
			realPath=ap.getValue()+(ap1==null?"":ap1.getValue());
		}
		return realPath;
	}
}
