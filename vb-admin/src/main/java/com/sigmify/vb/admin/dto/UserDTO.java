package com.sigmify.vb.admin.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sigmify.vb.admin.entity.metadata.UserType;

@JsonInclude(Include.NON_NULL)
public class UserDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8116414957433360698L;
	  private Long id;
	  private String userName;
	  private String fName;
	  private String lName;
	  private String photo;
	  private String isAgent;
	  private UserTypeDTO usertype;
	  private List<AddressDTO> addressdto;
	  private LocalDateTime createDate;
	  private LocalDateTime lastUpdateDate;
	  private boolean active;
	  private Long lastUpdateBy;
	  private UserContactDetailsDTO ucDto;
	  private UserPasswordDTO userPasswordDto;
	  private AccountDetailsDTO accDetailsDto;
	  private UserIdProofDTO userIdProofDto;
	  private ReferralDTO referralDto;
   
	//------------getter&setter method-----
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public UserTypeDTO getUsertype() {
		return usertype;
	}
	public void setUsertype(UserTypeDTO usertype) {
		this.usertype = usertype;
	}
	public List<AddressDTO> getAddressdto() {
		return addressdto;
	}
	public void setAddressdto(List<AddressDTO> addressdto) {
		this.addressdto = addressdto;
	}
	public LocalDateTime getCreateDate() {
		return createDate;
	}
	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}
	public LocalDateTime getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getIsAgent() {
		return isAgent;
	}
	public void setIsAgent(String isAgent) {
		this.isAgent = isAgent;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Long getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(Long lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public UserContactDetailsDTO getUcDto() {
		return ucDto;
	}
	public void setUcDto(UserContactDetailsDTO ucDto) {
		this.ucDto = ucDto;
	}
	public UserPasswordDTO getUserPasswordDto() {
		return userPasswordDto;
	}
	public void setUserPasswordDto(UserPasswordDTO userPasswordDto) {
		this.userPasswordDto = userPasswordDto;
	}
	public AccountDetailsDTO getAccDetailsDto() {
		return accDetailsDto;
	}
	public void setAccDetailsDto(AccountDetailsDTO accDetailsDto) {
		this.accDetailsDto = accDetailsDto;
	}
	 public ReferralDTO getReferralDto() {
		return referralDto;
	}
	public void setReferralDto(ReferralDTO referralDto) {
		this.referralDto = referralDto;
	}
	public UserIdProofDTO getUserIdProofDto() {
		return userIdProofDto;
	}
	public void setUserIdProofDto(UserIdProofDTO userIdProofDto) {
		this.userIdProofDto = userIdProofDto;
	}
	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", userName=" + userName + ", fName=" + fName + ", lName=" + lName + ", photo="
				+ photo + ", usertype=" + usertype + ", addressdto=" + addressdto + ", createDate=" + createDate
				+ ", lastUpdateDate=" + lastUpdateDate + ", active=" + active + ", lastUpdateBy=" + lastUpdateBy
				+ ", ucDto=" + ucDto + ", userPasswordDto=" + userPasswordDto + ", accDetailsDto=" + accDetailsDto
				+ ", userIdProofDto=" + userIdProofDto + ", referralDto=" + referralDto + "]";
	}
	
	
}
