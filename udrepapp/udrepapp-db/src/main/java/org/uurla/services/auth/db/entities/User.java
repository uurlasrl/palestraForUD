package org.uurla.services.auth.db.entities;

import java.io.Serializable;
import java.lang.String;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.uurla.services.PersistenceFactory;
import org.uurla.services.UurlaServiceKit;
import org.uurla.services.auth.PasswordEncryptionService;




/**
 * Entity implementation class for Entity: User
 *
 */
@Entity
@Table(name="uurlauser")
public class User implements Serializable {
	@Transient
	public String[][] getAuth(){
		return User.auth;
	}
	/*
	 * tutte le autorizzazioni che iniziano per "gen" devono essere assegnate a
	 * tutti i gruppi
	 */
	public static String[][] auth = {
			/*
			 * Visualizzazione consentita solo a se stessi
			 * ma può cancellare solo admin
			 */
			{ "", "gen000User-000", "CRU", "", "($$(0)$$).username = (££(username)££) OR 'admin' in (select ZIN2.rolename FROM User ZIN1 JOIN ZIN1.roles ZIN2 WHERE ZIN1.username = (££(username)££) )" },
			{ "", "gen000User-001", "D", "", "'admin' in (select ZIN2.rolename FROM User ZIN1 JOIN ZIN1.roles ZIN2 WHERE ZIN1.username = (££(username)££) )" }// può cancellare solo admin
	};
	
	@Id
	@Column(nullable=false, length=150)
	private String username;
	@Column(nullable=false, length=100)
	private String name;
	@Column(nullable=false, length=100)	
	private String password;
	
	@Column(nullable=true, length=100)	
	private String email;
	
	@Column(length=300)
	private String ownApp;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<Role> roles = new ArrayList<>();
	
	@Column(nullable=false) 	
	@Temporal(TemporalType.TIMESTAMP)
	private Date begdatim;
	
	@Column(nullable=false) 	
	@Temporal(TemporalType.TIMESTAMP)
	private Date enddatim;
	
	@Column(nullable=true, length=2)
	private String language;
	@Column(nullable=true, length=2)
	private String country;
	

	private Boolean enable;

	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<AuthGroup> authgroups = new ArrayList<>(); 
	
	private static final long serialVersionUID = 1L;

	public User() {
		super();
	}   
	
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Transient
	public List<AuthObject> getAutobjects(String tableName, EntityManager em,boolean read,boolean update, boolean create, boolean delete){
		PersistenceFactory pf=UurlaServiceKit.getPersistenceFactoryImpl();
		/*String qrstr="SELECT DISTINCT B3 "+ ""
				+ "FROM User B1 JOIN B1.authgroups B2 JOIN B2.authobjects B3 "
				+ "WHERE B1.username = :username AND ( B3.appName = :appname OR B3.appName = '' ) "+
				    "AND B3.tableName = :tablename AND (NOT(B3.objectName LIKE 'gen%')";*/
		
		String qrstr="SELECT DISTINCT OBJ "+ ""
				+ "FROM AuthObject OBJ LEFT JOIN OBJ.grpobjects GRP LEFT JOIN GRP.users USR "
				+ "WHERE OBJ.tableName = :tablename "
				+ "AND ( OBJ.appName = :appname OR OBJ.appName = '' ) "
				+ "AND ( OBJ.objectName LIKE 'gen%' OR USR.username = :username )";
		
		  if (create)  qrstr+=" AND OBJ.createRelevant = true";
		  if(delete)   qrstr+=" AND OBJ.deleteRelevant = true";
		  if(update)   qrstr+=" AND OBJ.updateRelevant = true";
		  if(read)     qrstr+=" AND OBJ.readRelevant = true";
		  qrstr+=" ORDER BY OBJ.pathName DESC";
		Query qr=em.createQuery(qrstr);
		
		qr.setParameter("username", getUsername());
		qr.setParameter("appname", pf.getAppName());
		qr.setParameter("tablename", tableName);
		
		List<AuthObject> resultList = (List<AuthObject>)qr.getResultList();
		return resultList;
	}
	@Transient
	public void setClearPassword(String pws) throws NoSuchAlgorithmException, InvalidKeySpecException{
		PasswordEncryptionService pes=new PasswordEncryptionService();
		this.setPassword(pes.convertToString(pes.getEncryptedPassword(pws)));
	}
	
	@Transient
	public void setApps(String[] apps){
		String tmpStr="";
		String comma="";
		for(int i=0;i<apps.length;i++){
			tmpStr+=comma+"\""+apps[i].trim()+"\"";
			comma=",";
		}
		setOwnApp(tmpStr);
	}
	
	@Transient
	public void addApp(String app){
		if(isForApp(app)) return; //esiste già
		
		String tmpStr=getOwnApp();
		String comma="";
		if(tmpStr!=null && tmpStr.length()>0){
			comma = ",";
		}else 
			tmpStr = "";
		tmpStr+=comma+"\""+app.trim()+"\"";
		setOwnApp(tmpStr);
	}

	@Transient
	public boolean isForApp(String app){
		String tmpStr=getOwnApp();
		if(tmpStr==null) return false;
		return tmpStr.indexOf("\""+app.trim()+"\"") >= 0;
	}
	
	
	public User(String username, String name, Date begdatim, Date enddatim, String language, String country, Boolean enable, String email) {
		super();
		this.username = username;
		this.name = name;
		this.begdatim = begdatim;
		this.enddatim = enddatim;
		this.language = language;
		this.country = country;
		this.enable = enable;
		this.email = email;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public List<AuthGroup> getAuthgroups() {
		return authgroups;
	}
	public void setAuthgroups(List<AuthGroup> authGroups) {
		this.authgroups = authGroups;
	}
	public String getOwnApp() {
		return ownApp;
	}
	public void setOwnApp(String ownApp) {
		this.ownApp = ownApp;
	}
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}   
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}   
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Date getBegdatim() {
		return begdatim;
	}
	public void setBegdatim(Date begdatim) {
		this.begdatim = begdatim;
	}
	public Date getEnddatim() {
		return enddatim;
	}
	public void setEnddatim(Date enddatim) {
		this.enddatim = enddatim;
	}
	public Boolean getEnable() {
		return enable;
	}
	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	@PostLoad
	public void postload(){
		/*
		EntityManagerFactory emf = JPAEntityManagerFactory.getEntityManagerFactory(BasicLoginModule.PUNIT_NAME);//Persistence.createEntityManagerFactory(PUNIT_NAME);
		EntityManager em = emf.createEntityManager();
		org.hibernate.Session session = (org.hibernate.Session) em.unwrap(org.hibernate.Session.class);
		// get the user
		User usr = em.find(User.class, "dneri");session.getProperties().
		*/
//		  PersistenceUtil util = Persistence.getPersistenceUtil();
		  
	}
}
