package com.sigmify.vb.admin.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(schema ="admin",name = "referral")
@XmlRootElement
public class Referral implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5231427076977781731L;

	@Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "referral_id_seq")
	  @SequenceGenerator(schema = "admin", name = "referral_id_seq",
	      sequenceName = "referral_id_seq", allocationSize = 1)
	  private Long id;
	
	@Column(name = "referral_code",unique = true,nullable = false)
	private String referralCode;
	
	@OneToOne(targetEntity = User.class,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "generated_for",referencedColumnName = "id")
	private User generatedfor;
	
	@OneToMany(targetEntity = ReferralCodeContact.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name="referral_code",referencedColumnName = "referral_code")
	private List<ReferralCodeContact> referralCodeContact;
	
	@Column(name = "total_referred")
	private Integer totalReferred;
	
	@Column(name = "total_installed")
	private Integer totalInstalled;
	
	@Column(name = "referral_point")
	private Integer referralPoint;

	public Integer getTotalReferred() {
		return totalReferred;
	}

	public void setTotalReferred(Integer totalReferred) {
		this.totalReferred = totalReferred;
	}

	public Integer getTotalInstalled() {
		return totalInstalled;
	}

	public void setTotalInstalled(Integer totalInstalled) {
		this.totalInstalled = totalInstalled;
	}

	public Integer getReferralPoint() {
		return referralPoint;
	}

	public void setReferralPoint(Integer referralPoint) {
		this.referralPoint = referralPoint;
	}

	//------getterSetter method
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReferralCode() {
		return referralCode;
	}

	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
	}

	

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public User getGeneratedfor() {
		return generatedfor;
	}

	public void setGeneratedfor(User generatedfor) {
		this.generatedfor = generatedfor;
	}

	public List<ReferralCodeContact> getReferralCodeContact() {
		return referralCodeContact;
	}

	public void setReferralCodeContact(List<ReferralCodeContact> referralCodeContact) {
		this.referralCodeContact = referralCodeContact;
	}

	//----toStringMethod
	@Override
	public String toString() {
		return "Referral [id=" + id + ", referralCode=" + referralCode + ", generatedFor=" + generatedfor + "]";
	} 
}

