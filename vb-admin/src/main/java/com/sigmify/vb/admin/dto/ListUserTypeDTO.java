package com.sigmify.vb.admin.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ListUserTypeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4971781038164841836L;
	
	private List<UserTypeDTO> listUserTypeDto;

	public List<UserTypeDTO> getListUserTypeDto() {
		return listUserTypeDto;
	}

	public void setListUserTypeDto(List<UserTypeDTO> listUserTypeDto) {
		this.listUserTypeDto = listUserTypeDto;
	}

}
