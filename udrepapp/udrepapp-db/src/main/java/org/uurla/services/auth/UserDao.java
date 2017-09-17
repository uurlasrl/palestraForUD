package org.uurla.services.auth;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Metamodel;

import org.uurla.services.auth.db.entities.AuthGroup;
import org.uurla.services.auth.db.entities.AuthObject;
import org.uurla.services.auth.db.entities.Role;
import org.uurla.services.auth.db.entities.User;
import org.uurla.services.db.entities.AppParameter;
import org.uurla.services.db.entities.Apps;
import org.uurla.services.db.entities.HandleConfiguration;
import org.uurla.services.db.entities.HandleConfigurationAuth;
import org.uurla.services.exception.UurlaDataException;

public class UserDao {
	private static UserDao daoobj = null;

	static public ArrayList<AuthObject> makeAuthfor(String tableName, String[][] authdes) {
		ArrayList<AuthObject> group = new ArrayList<AuthObject>();
		return makeAuthfor_internal(group, tableName, authdes);
	}

	static public ArrayList<AuthObject> makeAuthfor_internal(ArrayList<AuthObject> group, String tableName, String[][] authdes) {
		for (int i = 0; i < authdes.length; i++) {// CDUR
			// {"expense.svc","genExcenses","RUD","expenseHeader.project.resources.user","($$(4)$$).username
			// = (££(username)££)"},

			group.add(getNewAuthObjectFromStrArr(tableName, authdes[i]));

		}
		return group;
	}
	static public AuthObject getNewAuthObjectFromStrArr(String tableName, String[] authdes) {
	
	  return new AuthObject(authdes[1], // authName
			"generic", true, // grant
			null, // group
			tableName, // table
			authdes[0], // appname
			authdes[2].contains("C"), authdes[2].contains("D"), authdes[2].contains("U"), authdes[2].contains("R"), authdes[3], // path
			authdes[4]); // expression
	}
	
	public AuthObject delAuthObj(EntityManager em,String objectName){
		
		// lettura del gruppo autorizzativo
		AuthGroup ag=em.find(AuthGroup.class, "user");
		
		// estraggo la lista degli objauth se il mio oggetto è in questa lista devo eliminarlo
		List<AuthObject>lautobjInGroup=ag.getAuthobjects();
		
		// quindi leggo la lista l'object 
		AuthObject autho=em.find(AuthObject.class, objectName);
		
		if(autho==null) return null;
		
		//cancello gli oggetti dal gruppo
			lautobjInGroup.remove(autho);
			em.remove(autho);
			
		return	autho;
	}
	
	public HandleConfigurationAuth insertHandleClassAuth(EntityManager em,String className,
																		  String processType, 
																		  String methodName, //il metodo deve essere controllato prima 
																		  String rolename, 
																		  boolean active) throws UurlaDataException{

		Query qry=em.createQuery("SELECT A1 FROM HandleConfiguration A1 WHERE processType = '"+processType+"' AND javaClass = '"+className+"'");
		List<HandleConfiguration> lhc = qry.getResultList();
		if(lhc==null || lhc.size()==0)
			throw new UurlaDataException("processType:"+processType+",javaClass:"+className+" does not exist in the database");
		
		qry=em.createQuery("SELECT A1 FROM Role A1 WHERE rolename = '"+rolename+"'",Role.class);
		List<Role> lr = qry.getResultList();
		if(lr==null || lr.size()==0)
			throw new UurlaDataException("roleName:"+rolename+" does not exist in the database");

		qry=em.createQuery("SELECT A1 FROM HandleConfigurationAuth A1 WHERE "
				+ "processType = :processType AND "
				+ "javaClass = :javaClass AND "
				+ "method = :method AND "
				+ "rolename = :rolename",HandleConfigurationAuth.class);
		qry.setParameter("processType", processType);
		qry.setParameter("javaClass", className);
		qry.setParameter("method", methodName);
		qry.setParameter("rolename", rolename);
		
		List<HandleConfigurationAuth> lhca = qry.getResultList();
		if(lhca!=null && lhca.size()>0)
			throw new UurlaDataException("methodname:"+methodName+" in rolename:"+rolename+" has already been declared");
		
		
		HandleConfigurationAuth hca=new HandleConfigurationAuth(processType, className, methodName, rolename, active);
		
		em.persist(hca);

		return hca;
		
	}

	public HandleConfigurationAuth delHandleClassAuth(EntityManager em,String className,
			  String processType, 
			  String methodName, //il metodo deve essere controllato prima 
			  String rolename, 
			  boolean active) throws UurlaDataException{

		Query qry=em.createQuery("SELECT A1 FROM HandleConfigurationAuth A1 WHERE "
				+ "processType = :processType AND "
				+ "javaClass = :javaClass AND "
				+ "method = :method AND "
				+ "rolename = :rolename",HandleConfigurationAuth.class);

		qry.setParameter("processType", processType);
		qry.setParameter("javaClass", className);
		qry.setParameter("method", methodName);
		qry.setParameter("rolename", rolename);

		List<HandleConfigurationAuth> lhca = qry.getResultList();
		if(lhca==null || lhca.size()==0)
			throw new UurlaDataException("methodname:"+methodName+" in rolename:"+rolename+" does not exist in the database");


		HandleConfigurationAuth hca=new HandleConfigurationAuth(processType, className, methodName, rolename, active);

		em.remove(hca);

		return hca;

	}
	
	public String[] getListOfMethods(EntityManager em, Class classe, String roleCondition){		
		ArrayList<String> lmet=new ArrayList<String>();
		Query qr2 = em.createQuery("SELECT A1.method FROM HandleConfigurationAuth A1 WHERE javaClass = '"+classe.getName()+"' AND "+roleCondition +" GROUP BY A1.method",String.class );
		lmet=(ArrayList<String>) qr2.getResultList();
		if(lmet==null) return null;
		
/*		Method[] mets=classe.getMethods();
		for(Method met:mets){
		  if(met.getAnnotationsByType(EdmFunctionImport.class)!=null){
			  lmet.add(met.getName());
		  }
		}*/
		int siz=lmet.size();
		return (siz==0)?null:lmet.toArray(new String[siz]);
	}

	/*
	 * inserisce un AuthObject nel DB 
	 */
	public AuthObject insertAuthObj(EntityManager em,String  objectName,
			 									     String  tableName,
			 										 String  appName,
													 String  description,
													 String  pathName,
													 String  expression,
													 boolean granted,
													 boolean isC,
													 boolean isR,
													 boolean isU,
													 boolean isD) throws UurlaDataException, NoSuchFieldException, SecurityException{
		
		//data check
		if(tableName==null || tableName.length()<=0) 
			throw new UurlaDataException("table name mandatory");

		Metamodel mtm=em.getMetamodel();
		Set<EntityType<?>> entList=mtm.getEntities();
		HashMap<String, EntityType<?>> entMap=new HashMap<String, EntityType<?>>(entList.size()); 
		// luppo e memorizzo la lista delle tabelle
		Iterator iter = entList.iterator();
		EntityType ent=null;
		while(iter.hasNext()){
			ent = (EntityType)iter.next();
			entMap.put(ent.getName(), ent);			
		}
		
		//controllo se la tabella esiste
		ent=entMap.get(tableName);
		if(ent==null)
			throw new UurlaDataException("table:"+tableName+" must exist in the database");
		
		//verifico che almeno uno è true
		if(!(isC | isD | isR | isU))
			throw new UurlaDataException("atleast one of CRUD operation must be enabled");

		//controllo che il path sia corretto
		String lPathName="";
		if(pathName!=null)  lPathName=pathName;
		
		if(pathName.length()>0){
			//check del pathName
			String[] pathv=pathName.split("\\.");
			for(int i=0;i<pathv.length;i++){
				pathv[i]=pathv[i].trim();
				Attribute atr = ent.getAttribute(pathv[i]);
				if(atr==null) throw new UurlaDataException("element '"+pathv[i]+"' of the path '"+lPathName+"' does not exist");
				Class classObject=atr.getJavaType();
				if(classObject==null) throw new UurlaDataException("element '"+pathv[i]+"' of the path '"+lPathName+"' does not exist");
				String nameOfType=classObject.getSimpleName();
				if(nameOfType.equals("List") || nameOfType.equals("Set")){
					//leggo il tipo generico
					//nameOfType = ent.getJavaType().getDeclaredField(pathv[i]).getGenericType().getTypeName()
							nameOfType = ((Class)((ParameterizedType)(ent.getJavaType().getDeclaredField(pathv[i]).getGenericType())).getActualTypeArguments()[0]).getSimpleName(); 
				}
				ent=entMap.get(nameOfType);
			}
		}
		
		// controllo del nome oggetto
		if(!objectName.matches("[0-9]{3}"+tableName+"\\-[0-9]{3}$"))
			throw new UurlaDataException("the object name must be finish with nnn"+tableName+"-nnn");
		
		//controllo se l'oggetto esiste già
		AuthObject autho=em.find(AuthObject.class, objectName);
		if(autho!=null) 
			throw new UurlaDataException("the auth object:"+objectName + " already exist");


		String strarr[]={appName,objectName,(isC?"C":"")+ (isR?"R":"")+(isU?"U":"")+(isD?"D":""), pathName,expression};
		autho=getNewAuthObjectFromStrArr(tableName,strarr );
		autho.setGranted(granted);
		autho.setDescription(description);
		em.persist(autho);
		
		return	autho;
	}
	
	public List<AuthObject> getAuthObjByTable(EntityManager em,String table){
		// quindi leggo la lista di tutti gli oggetti associati alla mia tabella
		Query qry=em.createQuery("SELECT A1 FROM AuthObject A1 WHERE A1.tableName = '"+table+"'");
		return qry.getResultList();
	}

	public List<Apps> getAllApps(EntityManager em){
		// quindi leggo la lista di tutti gli oggetti associati alla mia tabella
		Query qry=em.createQuery("SELECT A1 FROM Apps A1");
		return qry.getResultList();
	}

	public Apps delApp(EntityManager em,String appName) throws UurlaDataException {
		
		Query qry=em.createQuery("SELECT A1 FROM AppParameter A1 where name like '"+appName+"-%'",AppParameter.class);
		List<AppParameter> lap=qry.getResultList();
		if(lap !=null && lap.size()>0) 
			throw new UurlaDataException("unable to complete app:"+appName+" has some entry in table AppParameter");
		
		qry=em.createQuery("SELECT A1 FROM AuthObject A1 where appName = '"+appName+"'",AuthObject.class);
		List<AuthObject> lao=qry.getResultList();
		if(lao !=null && lao.size()>0) 
			throw new UurlaDataException("unable to complete app:"+appName+" has entries in table AuthObject");

		qry=em.createQuery("SELECT A1 FROM User A1 where ownApp like '%\""+appName+"\"%'",User.class);
		List<User> lus=qry.getResultList();
		if(lus !=null && lus.size()>0) 
			throw new UurlaDataException("unable to complete app:"+appName+" has entries in table User");
		
		// quindi leggo la lista l'object 
		Apps appo=em.find(Apps.class, appName);
		
		if(appo==null)
			throw new UurlaDataException("unable to complete app:"+appName+" does not exist");
		
		//cancello gli oggetti dal gruppo
		em.remove(appo);
			
		return	appo;
		
	}

	public Apps insertNewApp(EntityManager em,String appName) throws UurlaDataException {
				
		Apps appo=em.find(Apps.class, appName);
		
		if(appo!=null)
			throw new UurlaDataException("unable to complete app:"+appName+" already exist");
		
		// cancello gli oggetti dal gruppo
		em.persist(appo);
		
		return	appo;
		
	}

	public List<HandleConfiguration> getAllHandleClass(EntityManager em,String processType){
		Query qry=em.createQuery("SELECT A1 FROM HandleConfiguration A1 WHERE processType = '"+processType+"'");
		return qry.getResultList();
	}

	public List<HandleConfigurationAuth> getHandleClassAuthByClass(EntityManager em,String className,String processType){

		Query qry=em.createQuery("SELECT A1 FROM HandleConfigurationAuth A1 WHERE processType = '"+processType+"' AND javaClass = '"+className+"'");
		return qry.getResultList();
	}

	private void deleteGenericAuthFromTable(EntityManager em, String table){
		em.getTransaction().begin();

		// lettura del gruppo autorizzativo
		AuthGroup ag=em.find(AuthGroup.class, "user");
		
		// estraggo la list degli objauth
		List<AuthObject>lautobjInGroup=ag.getAuthobjects();
		
		// quindi leggo la lista di tutti gli oggetti associati alla mia tabella
		Query qry=em.createQuery("SELECT A1 FROM AuthObject A1 WHERE A1.tableName = '"+table+"'");
		List<AuthObject> lauth=qry.getResultList();
		
		//cancello gli oggetti dal gruppo
		for(AuthObject auth : lauth){
			lautobjInGroup.remove(auth);
			em.remove(auth);
		}

		//qry=em.createQuery("DELETE FROM AuthObject A1 WHERE A1.tableName = '"+table+"'");
		//qry.executeUpdate();
		
		//riassegno la lista dopo che ho eliminato gli oggetti da togliere
		ag.setAuthobjects(lautobjInGroup);
		
		em.flush();
		
		em.getTransaction().commit();
	}
	
	public List<String> getListOfTable(EntityManager em){
		
		ArrayList<String> as = new ArrayList<String>();
		//entity list
		Metamodel mtm=em.getMetamodel();
		Set entList=mtm.getEntities();

		// luppo la lista delle tabelle
		Iterator iter = entList.iterator();
		while(iter.hasNext()){
			as.add(((EntityType)iter.next()).getName());
		}
		return as;

	}

	public String refreshAutorizationOfTable(EntityManager em, String table,boolean all) throws UurlaDataException{
		String tableList=null;
		//entity list
		Metamodel mtm=em.getMetamodel();
		Set entList=mtm.getEntities();
		
		// luppo la lista delle tabelle
		Iterator iter = entList.iterator();
		while(iter.hasNext()){
			Object el=iter.next();
			if (((EntityType)el).getName().equals(table) || all ){
				Method met=null;
				try {
					//classe entity
					Class classOfTable=((EntityType)el).getBindableJavaType();
					
					//nuova instanza
					Object instancedObj=classOfTable.newInstance();
					//lettura del metodo getAuth ed esecuzione
					met=classOfTable.getMethod("getAuth",null);
					String[][] authData=(String[][]) met.invoke(instancedObj, null);
					
					//cancelazione delle autorizzazioni
					deleteGenericAuthFromTable(em,((EntityType)el).getName());
					//em.flush();
					em.getTransaction().begin();
					
					//scrittura delle autorizzazioni
					
					// lettura del gruppo autorizzativo
					AuthGroup ag=em.find(AuthGroup.class, "user");
					// Lettura dell'arraylist con gli oggetti autorizzativi
					List<AuthObject> group = ag.getAuthobjects();
					List<AuthObject> grouptmp = UserDao.makeAuthfor(((EntityType)el).getName(), authData);
					//group.addAll(grouptmp);

					for (AuthObject obj : grouptmp) {
						em.persist(obj);
					}
					em.getTransaction().commit();					
					getLogger().log(Level.INFO, " table:" + ((EntityType)el).getName() + " default authorization reset complete");
					if (all) tableList= ((tableList==null)?(((EntityType)el).getName()):(tableList+", "+((EntityType)el).getName()));
					else tableList = ((EntityType)el).getName();
				} catch (NoSuchMethodException | SecurityException e) {
					getLogger().log(Level.WARNING, "missing method getAuth in table:" + ((EntityType)el).getName());
					if(!all)
					  throw new UurlaDataException("missing method getAuth in table:" + ((EntityType)el).getName());
					//e.printStackTrace();
				} catch (InstantiationException e) {
					getLogger().log(Level.WARNING, e.getMessage() +" for table " + ((EntityType)el).getName());
					if(!all)
						  throw new UurlaDataException(e.getMessage() +" for table " + ((EntityType)el).getName());
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					getLogger().log(Level.WARNING, e.getMessage() +" for table " + ((EntityType)el).getName(),e);
					if(!all)
						  throw new UurlaDataException(e.getMessage() +" for table " + ((EntityType)el).getName());
				} catch (IllegalArgumentException e) {
					getLogger().log(Level.WARNING, e.getMessage() +" for table " + ((EntityType)el).getName(),e);
					if(!all)
						  throw new UurlaDataException(e.getMessage() +" for table " + ((EntityType)el).getName());
				} catch (InvocationTargetException e) {
					getLogger().log(Level.WARNING, e.getMessage() +" for table " + ((EntityType)el).getName(),e);
					if(!all)
						  throw new UurlaDataException(e.getMessage() +" for table " + ((EntityType)el).getName());
				}finally{
					if(em.getTransaction().isActive()){
						em.getTransaction().rollback();
					}
				}
			}
		}
		
		//(((EntityType)el).getBindableJavaType().getDeclaredField("auth"))).getCurrencyObj(em, curtxt)
		//ArrayList<AuthObject> authObj=makeAuthfor(table, Currency.auth));
		if (all)					return " tables:" + tableList + " ;default authorization reset complete";
		else if(tableList == null)	return " table " + table  + " does not exist";
		else     					return " table " + tableList  + " ;default authorization reset complete";
	}
	
	
	public User addNewUser(EntityManager em, String uname, String password, String email, String fullname) throws Exception{

		//Find (select) sulla tabella per trovare se l'username esiste
		User u = em.find(User.class, uname);		
		//Imposta l'email tutto in minuscolo (per evitare problemi di case sensitive)
		String temail = email.toLowerCase();		
		//Se l'username è già esistente sul db
		if(u != null){
			getLogger().log(Level.SEVERE, "user should not be exist");
			throw new Exception("user should not be exist");
		}
		
		//Se l'email è null
		if(email==null || email.trim().length()==0){
			getLogger().log(Level.SEVERE, "please enter an valid email");
			throw new Exception("please enter an valid email");
		}
		
		//Se l'email è già esistente sul db
		Query qr=em.createQuery("SELECT A1 FROM User A1 WHERE email=:email");		
		qr.setParameter("email", temail);		
		List<User> lu=qr.getResultList();		
		//Va a controllare se la lista è vuota (se ci sono email uguali)
		if (!lu.isEmpty()) {
			getLogger().log(Level.SEVERE, "email should not be exist");
			throw new Exception("email should not be exist");
		}
		
		//Se l'email inserita è valida (RFC822 compliant regex)
		Pattern ptr = Pattern.compile("(?:(?:\\r\\n)?[ \\t])*(?:(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*)|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*:(?:(?:\\r\\n)?[ \\t])*(?:(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*)(?:,\\s*(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*))*)?;\\s*)");
		//(se è false)
		if (!ptr.matcher(temail).matches()) {
			getLogger().log(Level.SEVERE, "please enter an valid email");
			throw new Exception("please enter an valid email");
		}
		
		
		
		Role ruser = em.find(Role.class, "users");

		Calendar bdatetime = Calendar.getInstance();
		bdatetime.set(2016, 11, 1, 1, 1, 1);
		Calendar edatetime = Calendar.getInstance();
		edatetime.set(2099, 10, 1, 1, 1, 1);

		u = new User(uname, fullname, new Date(bdatetime.getTimeInMillis()), new Date(edatetime.getTimeInMillis()), null, null, true, temail);
		
		try {
			//va ad criptare la password
			u.setClearPassword(password);
		} catch (Exception e) {
			// getLogger().log(Level.SEVERE, "Error in ResourceBundle creation", e);
			e.printStackTrace();
		}
		u.addApp("projman.svc");
		u.addApp("expense.svc");
		u.addApp("planning.svc");
		u.addApp("dayrecord.svc");
		u.addApp("userman.svc");
		List<Role> rl = u.getRoles();
		
		rl.add(ruser);
		
		u.setRoles(rl);
		
		em.persist(u);

		return u;
	}
	
	public User editUser(EntityManager em, String uname, String password, String email, String fullname) throws Exception{

		//Find (select) sulla tabella per trovare se l'username esiste
		User u = em.find(User.class, uname);		
		//Imposta l'email tutto in minuscolo (per evitare problemi di case sensitive)
		String temail = email.toLowerCase();
		
		//Se l'utente non esiste
		if(u == null){
			getLogger().log(Level.SEVERE, "user should be exist");
			throw new Exception("user should be exist");
		}
		
		//Se l'email è null
		if(email==null || email.trim().length()==0){
			getLogger().log(Level.SEVERE, "please enter an valid email");
			throw new Exception("please enter an valid email");
		}
		
		//Se il fullname è null
		if(fullname==null || fullname.trim().length()==0){
			getLogger().log(Level.SEVERE, "please enter the full name");
			throw new Exception("please enter the full name");
		}
		
		//Se l'email è già esistente sul db (dove l'username non è uguale al proprio)
		Query qr=em.createQuery("SELECT A1 FROM User A1 WHERE email=:email AND username<>:uname");		
		qr.setParameter("email", temail);		
		qr.setParameter("uname", uname);
		List<User> lu=qr.getResultList();		
		//Va a controllare se la lista è vuota (se ci sono email uguali)
		if (!lu.isEmpty()) {
			getLogger().log(Level.SEVERE, "email should not be exist");
			throw new Exception("email should not be exist");
		}
		
		//Se l'email inserita è valida (RFC822 compliant regex)
		Pattern ptr = Pattern.compile("(?:(?:\\r\\n)?[ \\t])*(?:(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*)|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*:(?:(?:\\r\\n)?[ \\t])*(?:(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*)(?:,\\s*(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*))*)?;\\s*)");
		//(se è false)
		if (!ptr.matcher(temail).matches()) {
			getLogger().log(Level.SEVERE, "please enter an valid email");
			throw new Exception("please enter an valid email");
		}		
						
		//se è stata inserita una nuova password, va a criptarla ed inserirla
		if (password != null && password.trim().length() != 0) {
			try {
				//va ad criptare la password
				u.setClearPassword(password);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		
		//va ad inserire gli altri campi (fullname, email)		
		u.setEmail(temail);
		u.setName(fullname);
		
		return u;
	}
	
	
	public static UserDao getInstance() {
		if (daoobj == null) {
			daoobj = new UserDao();
		}
		return daoobj;
	}

	public User getUserObj(EntityManager em, String uname) {
		if (uname == null || uname.length() == 0)
			return null;
		return em.find(User.class, uname);
	}

	public User getUserObjAndCheckExistence(EntityManager em, String uname) throws Exception {
		if (uname == null || uname.length() == 0){
			getLogger().log(Level.SEVERE, "user is mandatory");
			throw new Exception("currency is mandatory");
		}
		User userObj = getUserObj(em, uname);

		if (userObj == null) {
			getLogger().log(Level.SEVERE, "user:" + uname + " not found");
			throw new Exception("user:" + uname + " not found");
		}
		return userObj;
	}

	protected Logger getLogger(){
		return Logger.getLogger(this.getClass().getName());
	}
	
	
	
	
}
