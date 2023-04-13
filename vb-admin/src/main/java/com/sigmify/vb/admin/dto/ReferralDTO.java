package com.sigmify.vb.admin.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@XmlRootElement
public class ReferralDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 742585157638870238L;
  private Long id;
  private String referralCode;
  private Long generatedFor;
  private List<ReferralCodeContactDTO> refercodeContactDto;
  private Integer totalReferred;
  private Integer totalInstalled;
  private Integer referralPoint;
  private String phone;
  private String name;
  
  //----getterSetter method
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
  public Long getGeneratedFor() {
    return generatedFor;
  }
  public void setGeneratedFor(Long generatedFor) {
    this.generatedFor = generatedFor;
  }
  public List<ReferralCodeContactDTO> getRefercodeContactDto() {
    return refercodeContactDto;
  }
  public void setRefercodeContactDto(List<ReferralCodeContactDTO> refercodeContactDto) {
    this.refercodeContactDto = refercodeContactDto;
  }
  public static long getSerialversionuid() {
    return serialVersionUID;
  }
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
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getPhone() {
    return phone;
  }
  public void setPhone(String phone) {
    this.phone = phone;
  }
  
}