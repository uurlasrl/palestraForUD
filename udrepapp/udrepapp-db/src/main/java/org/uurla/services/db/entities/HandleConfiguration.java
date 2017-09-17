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
@Entity

public class HandleConfiguration implements Serializable {
	@Transient
	public String[][] getAuth(){
		return HandleConfiguration.auth;
	}
	public static String[][] auth = {
			/*
			 * Nessuno può fare niente
			 */
			{ "", "gen000HandleConfiguration-000", "CRUD", "name", "-1" }
	};	
	   
	/**
	 * 
	 */
	private static final long serialVersionUID = 4915025003929675322L;
	
	// key	
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	
	@Column(nullable=false,length=100)
	private String processType;
	
	@Column(nullable=false,length=255)
	private String javaClass;

	public HandleConfiguration() {
		super();
	}   
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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
	public HandleConfiguration(String processType, String javaClass) {
		super();
		this.processType = processType;
		this.javaClass = javaClass;
	}
   
}
