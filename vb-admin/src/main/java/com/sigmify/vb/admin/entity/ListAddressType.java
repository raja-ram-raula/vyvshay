package com.sigmify.vb.admin.entity;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sigmify.vb.admin.entity.metadata.AddressType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ListAddressType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3713555601365059073L;
	
	@XmlElement(name = "addressType")
	private List<AddressType> listAddressType;

	public List<AddressType> getListAddressType() {
		return listAddressType;
	}

	public void setListAddressType(List<AddressType> listAddressType) {
		this.listAddressType = listAddressType;
	}

}
