package org.uurla.ud.udrepapp.db.entities;

import java.io.Serializable;
import java.lang.Long;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.uurla.services.UurlaServiceKit;

/**
 * Entity implementation class for Entity: Projectlog
 *
 */
@Entity
@Table(name="projectlog")
public class Projectlog implements Serializable {
	@Transient
	public String[][] getAuth(){
		return new String[0][0];
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -3281127896283431019L;
	
    @Id
    @SequenceGenerator(name="projectlog_id_seq",
                       sequenceName="projectlog_id_seq",
                       allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator="projectlog_id_seq")
    @Column(name = "id", updatable=false)
	private Long id;

    
	//@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)//, generator="my_entity_seq_gen")
	//@SequenceGenerator(name="my_entity_seq_gen", sequenceName="MY_ENTITY_SEQ")
	
	// key
/*	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	*/
	
//	@ManyToOne
//	@Basic(optional = false)
//	private Project project;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date logdatetime;
	
	
//	@ManyToOne
//	@Basic(optional = false)
//	private Resource resource;

//	@ManyToOne
//	@Basic(optional = false)
//	private ProjectlogType type;
	
	@Column(nullable = true, length = 1000)
	private String description;
	@Column(nullable = false, length = 200)
	private String cfield;
	@Column(nullable = false, length = 200)
	private String cvalOld;
	@Column(nullable = false, length = 200)
	private String cvalNew; 

	// Versioning and responsability
	@Basic(optional = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	@Basic(optional = false)
	private String usrCreated;
  

	
	
	public Projectlog() {
		super();
	}   
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public Date getLogdatetime() {
		return logdatetime;
	}
	public void setLogdatetime(Date logdatetime) {
		this.logdatetime = logdatetime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String descriptio1) {
		this.description = descriptio1;
	}
	public String getCfield() {
		return cfield;
	}
	public void setCfield(String cfield) {
		this.cfield = cfield;
	}
	public String getCvalOld() {
		return cvalOld;
	}
	public void setCvalOld(String cvalOld) {
		this.cvalOld = cvalOld;
	}
	public String getCvalNew() {
		return cvalNew;
	}
	public void setCvalNew(String cvalNew) {
		this.cvalNew = cvalNew;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getUsrCreated() {
		return usrCreated;
	}
	public void setUsrCreated(String usrCreated) {
		this.usrCreated = usrCreated;
	}

	@PrePersist
	public void prepersist() {
		// Gestione data e utente di creazione
		Date dt=Calendar.getInstance().getTime();
		this.setLogdatetime(dt);
		String uname = UurlaServiceKit.getPersistenceFactoryImpl().getUser();
		setUsrCreated(uname);
		setCreated(dt);
	}

	
}
