package org.uurla.ud.udrepapp.db.entities;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: TestTable
 *
 */
@Entity

public class TestTable implements Serializable {

	   
	@Id
	private Long Id;
	private String Name;
	private String Type;
	private Long Days;
	private static final long serialVersionUID = 1L;

	public TestTable() {
		super();
	}   
	public Long getId() {
		return this.Id;
	}

	public void setId(Long Id) {
		this.Id = Id;
	}   
	public String getName() {
		return this.Name;
	}

	public void setName(String Name) {
		this.Name = Name;
	}   
	public String getType() {
		return this.Type;
	}

	public void setType(String Type) {
		this.Type = Type;
	}   
	public Long getDays() {
		return this.Days;
	}

	public void setDays(Long Days) {
		this.Days = Days;
	}
	public TestTable(Long id, String name, String type, Long days) {
		super();
		Id = id;
		Name = name;
		Type = type;
		Days = days;
	}
   
}
