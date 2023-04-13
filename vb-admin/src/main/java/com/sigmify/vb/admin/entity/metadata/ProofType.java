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
@Table(schema = "admin",name = "proof_type")
public class ProofType implements Serializable {
	@Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "proof_type_id_seq")
	  @SequenceGenerator(schema = "admin", name = "proof_type_id_seq",
	      sequenceName = "proof_type_id_seq", allocationSize = 1)
	  private Integer id;
	
	  @Column(name = "name",length = 50,nullable = false)
	  private String name;
	  
	  @Column(name="description",length = 80,nullable = false)
	  private String description;

	//---settergetter id
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

	//----toString method
	@Override
	public String toString() {
		return "ProofType [id=" + id + ", name=" + name + ", description=" + description + "]";
	}
	  
	
}
