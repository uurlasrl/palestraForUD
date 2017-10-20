package org.uurla.services.auth.db.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;



/**
 * Entity implementation class for Entity: User
 * Could not determine type for: java.util.Set, at table: uurlaauthobj, for columns: [org.hibernate.mapping.Column(grpobjects)]
 */
//@Entity
//@Table(name="uurlaauthgrp")
public class AuthGroup{
	@Transient
	public String[][] getAuth(){
		return AuthGroup.auth;
	}
	public static String[][] auth = {
			/*
			 * Visualizzazione consentita solo ai membri
			 */
			{ "", "gen000AuthGroup-000", "CRU", "users", "($$(1)$$).username = (££(username)££) OR 'admin' in (select ZIN2.rolename FROM User ZIN1 JOIN ZIN1.roles ZIN2 WHERE ZIN1.username = (££(username)££) )" },
			{ "", "gen000AuthGroup-001", "D", "", "'admin' in (select ZIN2.rolename FROM User ZIN1 JOIN ZIN1.roles ZIN2 WHERE ZIN1.username = (££(username)££) )" }// può cancellare solo admin
	};


	@Id
	@Column(name="group_name",unique = true, length = 40)
	String groupName;
	
	@Column(length = 200)
	String description;
	
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<AuthObject> authobjects = new ArrayList<AuthObject>();

	@ManyToMany(mappedBy="authgroups")
	private List<User> users = new ArrayList<>();
	
	
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<AuthObject> getAuthobjects() {
		return authobjects;
	}

	public void setAuthobjects(List<AuthObject> authobjects) {
		this.authobjects = authobjects;
	}

	public AuthGroup(String groupName, String description, List<AuthObject> authobjects) {
		super();
		this.groupName = groupName;
		this.description = description;
		if(authobjects==null)
			this.authobjects = new ArrayList<AuthObject>();
		else;
			this.authobjects = authobjects;
	}

	public AuthGroup() {
	}
    
}
