package com.sigmify.vb.admin.entity.metadata;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(schema = "admin",name = "district")
public class District implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5270139264344983512L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "district_id_seq")
	  @SequenceGenerator(schema = "admin", name = "district_id_seq",
	      sequenceName = "district_id_seq", allocationSize = 1)
	private Integer id;
	
	@Column(name = "name",length = 50,nullable = false)
	private String name;
	
	@Column(name = "description",length = 50,nullable = false)
	private String description;
	
	@ManyToOne(targetEntity = State.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "state_code",referencedColumnName = "name")
	private State stateCode;

	//----getterSetter method
	
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

	public State getStateCode() {
		return stateCode;
	}

	public void setStateCode(State stateCode) {
		this.stateCode = stateCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	//----toString method
	@Override
	public String toString() {
		return "District [id=" + id + ", name=" + name + ", description=" + description + ", stateCode=" + stateCode
				+ "]";
	}
}
