package org.uurla.services.auth.db.entities;

import java.io.Serializable;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


/**
 * Entity implementation class for Entity: Role
 *
 */
//@Entity
//@Table(name="uurlarole")
public class Role implements Serializable {
	@Transient
	public String[][] getAuth(){
		return Role.auth;
	}
	/*
	 * tutte le autorizzazioni che iniziano per "gen" si assegnano automaticamente
	 * tutti i gruppi
	 */
	public static String[][] auth = {
			/*
			 * Visualizzazione consentita solo ai membri
			 */
			{ "", "gen000Role-000", "CRU", "users", "($$(1)$$).username = (££(username)££) OR 'admin' in (select ZIN2.rolename FROM User ZIN1 JOIN ZIN1.roles ZIN2 WHERE ZIN1.username = (££(username)££) )" },
			{ "", "gen000Role-001", "D", "", "'admin' in (select ZIN2.rolename FROM User ZIN1 JOIN ZIN1.roles ZIN2 WHERE ZIN1.username = (££(username)££) )" }// può cancellare solo admin
	};

	   
	@Id
	private String rolename;
	
	private String description;
	
	@ManyToMany(mappedBy="roles")
	private List<User> users = new ArrayList<>();
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	private static final long serialVersionUID = 1L;

	public Role() {
		super();
	}   
	
	public String getRolename() {
		return this.rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}   
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
   
}
