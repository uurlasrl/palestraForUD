package org.uurla.services.db.entities;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;


/**
 * Entity implementation class for Entity: AppParameter
 *
 */
@Entity

public class AppParameter implements Serializable {
	@Transient
	public String[][] getAuth(){
		return AppParameter.auth;
	}
	public static String[][] auth = {
			/*
			 * Nessuno può fare niente
			 */
			{ "", "gen000AppParameter-000", "CRUD", "name", "-1" }
	};
	   
	@Id
	@Column(length=100)
	private String name;
	@Column(length=100)
	private String value;
	private static final long serialVersionUID = 1L;

	public AppParameter() {
		super();
	}   

	public AppParameter(String iniNam,String iniVal) {
		super();
		setName(iniNam);
		setValue(iniVal);
	}   
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}   
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}
   
}
