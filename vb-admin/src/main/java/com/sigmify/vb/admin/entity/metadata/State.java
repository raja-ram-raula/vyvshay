package com.sigmify.vb.admin.entity.metadata;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(schema ="admin" ,name = "state")
public class State implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6933269929699603180L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "state_id_seq")
	  @SequenceGenerator(schema = "admin", name = "state_id_seq",
	      sequenceName = "state_id_seq", allocationSize = 1)
	private Integer id; 
	
	@Column(name = "name",length = 50,nullable = false)
	private String name;
	
	@Column(name = "description",length = 50,nullable = false)
	private String description;
	
	@Column(name = "state_code",length = 20,nullable = false)
	private String stateCode;

//----GetterSetter method----
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
//----toString method
	@Override
	public String toString() {
		return "State [id=" + id + ", name=" + name + ", description=" + description + ", stateCode=" + stateCode + "]";
	}
	
	
	
}
