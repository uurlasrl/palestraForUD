package org.uurla.services.db.entities;

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
@Table(name="uurlaapps")
public class Apps implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7830264075946269046L;


	@Transient
	public String[][] getAuth(){
		return auth;
	}
	public static String[][] auth = {
			/*
			 * Nessuno può fare niente
			 */
			{ "", "gen000Apps-000", "CRUD", "", "($$(1)$$).appname = '1'" }
	};	
	
	@Id
	@Column(nullable=false, length=150)
	private String appname;
	

	public Apps() {
		super();
	}   
	
}
