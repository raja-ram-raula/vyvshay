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
@Table(schema = "admin",name = "terms_and_conditions")
public class TermsAndConditions implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3221248772167137780L;
	
	@Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "terms_and_coditions_id_seq")
	  @SequenceGenerator(schema = "admin", name = "terms_and_coditions_id_seq",
	      sequenceName = "terms_and_coditions_seq", allocationSize = 1)
	  private Integer id;
	  
	  @Column(name = "name",length = 50)
	  private String name;

	  @Column(name="description",length = 500)
	  private String description;

	//-----getter setter method
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}




}
