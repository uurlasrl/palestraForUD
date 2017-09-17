package org.uurla.services.auth.db.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;


/*
 * I record di out object vengono creati per l'applicazione web che bisogna creare
 * Ogni record si riferisce ad una tabella e definisce le regole di accesso alla tabella
 * 
 */
@Entity
@Table(name="uurlaauthobj")
public class AuthObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 223289781960402315L;



	@Transient
	public String[][] getAuth(){
		return AuthObject.auth;
	}
	/*
	 * solo admin può guardare
	 */
	public static String[][] auth = {
	  { "", "gen000AuthObject-001", "CRUD", "", "'admin' in (select ZIN2.rolename FROM User ZIN1 JOIN ZIN1.roles ZIN2 WHERE ZIN1.username = (££(username)££) )" }
	};

	// key
	@Id
	@Column(name="object_name",unique = true, length = 100)
	String objectName;
	
	@Column(length = 200)
	String description;
	
	
	boolean granted=false;
	
	@ManyToMany(mappedBy="authobjects")
    @Column(name="grpobjects")
	List<AuthGroup> grpobjects = new ArrayList<AuthGroup>(0);
    
	/*
	 * Il nome della tabella sulla quale agisce l'oggetto autorizzativo
	 */
	@Column(length = 100)
	String tableName;

	/*
	 * app name che sarebbe il path del servizio cioè della servlet dove risponde olingo
	 */
	@Column(length = 100)
	String appName;
	
	boolean createRelevant;
	boolean deleteRelevant;
	boolean updateRelevant;
	boolean readRelevant;
	
	
	
	/*
     * Path of the object to be check
     * Questo path sarà composto dal percorso dei link dalla tabella tableName passando per gli oggetti 
     * elencati nell'espressione
     * patname può essere vuoto oppure contenere una concatenazione di relazioni, quelle presenti negli 
     * entity
     * tasks.resources.user
     * in questo caso task sarà identificato con 1, resources con 2 e user con 3
     * partendo datta tabella Projects che può avere diversi task si passa per la tabella risorse 
     * con deiverse risorse associate a un task e ogni risorsa può esserre associata ad un solo utente
     */
	@Column(length = 150)
	String pathName;

	/*
	 * Expression può essere ad esempio:
	 * 
	 * $$(0)$$.projectType = 'Progetto1'
	 * In questo caso la condizione è associata alla tabella principale
	 * SELECT B1 FROM Project B1 WHERE B1.projectType = 'Progetto1'
	 * 
	 * $$(0)$$.projectType = 'Progetto1' AND ($$(3)$$).username = (££(username)££)
	 * In questo caso la condizione è associata alla tabella principale
	 * SELECT B1 FROM Project B1 JOIN B1.tasks B2 JOIN B2.resources B3 JOIN B3.user B4
	 *   WHERE B1.projectType = 'Progetto1' AND B4.username = 'marioneri'
	 * 
	 */
	@Column(length = 300)
	String expression;
	
	/*
	 * Could not determine type for: java.util.Set, at table: uurlaauthobj, for columns: [org.hibernate.mapping.Column(grpobjects)]
	 * org.hibernate.MappingException: 
	 * Foreign key (FK9lpkinoij0e13fl1jqs41wor5:uurlaauthobj [object___name])) 
	 * must have same number of columns as the referenced primary key (
	 * uurlaauthobj [group___name,object___name])
	 * Type of the comparition
	 * = equal
	 * <> disegual
	 * < less
	 * > greater
	 * <= less or equal
	 * >= greater or equal
	 * LIKE like operator
    */

	/*
	 * Type of Pattern string
	 * 001 = value
	 * 002 = value ignore case (I can not use)
	 * 003 = regex (I can not use)
	 * 004 = path  (field of the User table)
	 */
	
	
	public String getObjectName() {
		return objectName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public boolean isCreateRelevant() {
		return createRelevant;
	}

	public void setCreateRelevant(boolean createRelevant) {
		this.createRelevant = createRelevant;
	}

	public boolean isDeleteRelevant() {
		return deleteRelevant;
	}

	public void setDeleteRelevant(boolean deleteRelevant) {
		this.deleteRelevant = deleteRelevant;
	}

	public boolean isUpdateRelevant() {
		return updateRelevant;
	}

	public void setUpdateRelevant(boolean updateRelevant) {
		this.updateRelevant = updateRelevant;
	}

	public boolean isReadRelevant() {
		return readRelevant;
	}

	public void setReadRelevant(boolean readRelevant) {
		this.readRelevant = readRelevant;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isGranted() {
		return granted;
	}

	public void setGranted(boolean granted) {
		this.granted = granted;
	}

	public List<AuthGroup> getGrpobjects() {
		return grpobjects;
	}

	public void setGrpobjects(List<AuthGroup> grpobjects) {
		this.grpobjects = grpobjects;
	}

	public String getPathName() {
		return pathName;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	
	
	public AuthObject(String objectName, String description, boolean granted, List<AuthGroup> grpobjects,
			String tableName, String appName, boolean createRelevant, boolean deleteRelevant, boolean updateRelevant,
			boolean readRelevant, String pathName, String expression) {
		super();
		this.objectName = objectName;
		this.description = description;
		this.granted = granted;
		if(grpobjects==null)
			grpobjects = new ArrayList<AuthGroup>(0);
		else
			this.grpobjects = grpobjects;
		this.tableName = tableName;
		this.appName = appName;
		this.createRelevant = createRelevant;
		this.deleteRelevant = deleteRelevant;
		this.updateRelevant = updateRelevant;
		this.readRelevant = readRelevant;
		this.pathName = pathName;
		this.expression = expression;
	}

	public AuthObject() {
	}
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		AuthObject auo = (AuthObject) o;
		return ((objectName==null)?"":objectName).equals((auo.objectName==null)?"":auo.objectName);
	}
}
