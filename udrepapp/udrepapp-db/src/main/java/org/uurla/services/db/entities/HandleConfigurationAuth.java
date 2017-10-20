package org.uurla.services.db.entities;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * Entity implementation class for Entity: HandleConfiguration
 *
 */
//@Entity

public class HandleConfigurationAuth implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4962832781792588154L;

	@Transient
	public String[][] getAuth(){
		return HandleConfigurationAuth.auth;
	}
	public static String[][] auth = {
			/*
			 * Nessuno può fare niente
			 */
			{ "", "gen000HandleConfigurationAuth-000", "CRUD", "name", "-1" }
	};	
	   
	// key
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	
	@Column(nullable=false,length=100)
	private String processType;
	
	@Column(nullable=false,length=255)
	private String javaClass;

	@Column(nullable=false,length=255)
	private String method;
	
	@Column(nullable=false,length=20)
	private String rolename;

	@Column(nullable=false,length=20)
	private boolean active=false;
	
	public HandleConfigurationAuth() {
		super();
	}   

	
	
	public HandleConfigurationAuth(String processType, String javaClass, String method, String rolename,
			boolean active) {
		super();
		this.processType = processType;
		this.javaClass = javaClass;
		this.method = method;
		this.rolename = rolename;
		this.active = active;
	}



	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}   
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getProcessType() {
		return this.processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}   
	public String getJavaClass() {
		return this.javaClass;
	}

	public void setJavaClass(String javaClass) {
		this.javaClass = javaClass;
	}
	public HandleConfigurationAuth(String processType, String javaClass) {
		super();
		this.processType = processType;
		this.javaClass = javaClass;
	}
   
}
