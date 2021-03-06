package org.uurla.services;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.persistence.EntityManagerFactory;

public interface PersistenceFactory {

	public void setODataJPAContext(Object obj);
	public Object getODataJPAContext();
	public EntityManagerFactory getEmf();
	public EntityManagerFactory getEmfAdmin();
	
	public void clearUser();
	public void setUser(String login, List<String> groups);
	public String getUser();
	public boolean isInGroup(String group);
	public List<String> getGroups();
	public String getPersistenceUnitName();
	public String getPersistenceUnitNameAdmin();
	public void setAppName(String appName);
	public String getAppName();
	public void setContainerLocale(Locale l);
	public void initUser();
	public void setOdataContext(Object context);
	public Object getOdataContext();
	public String getAppParameterValue(String name, boolean bypassbuffer);
	public void setEntityDataModel(Object edm);
	public Object getEntityDataModel();
	public void setRealPath(String str);
	public String getRealPath();
	public boolean isRole(String role);
}
