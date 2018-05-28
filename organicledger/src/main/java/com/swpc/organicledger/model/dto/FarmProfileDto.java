package com.swpc.organicledger.model.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "farmCode")
public class FarmProfileDto implements Serializable {

	@Size(max=10, message = "{farmprofile.farmcode.toolong}")
	private String farmCode;

	@NotEmpty(message = "{farmprofile.name.required}")
	@Size(max=100, message = "{farmprofile.name.toolong}")
	private String name;

	@Size(max=500, message = "{farmprofile.address.toolong}")
	private String address;

	@Size(max=100, message = "{farmprofile.gmapaddress.toolong}")
	private String gmapAddress;

	@Size(max=500, message = "{farmprofile.description.toolong}")
	private String description;

	@NotEmpty(message = "{farmprofile.farmername.required}")
	@Size(max=255, message = "{farmprofile.farmername.toolong}")
	private String farmerName;

	@Size(max=255, message = "{farmprofile.farmerimage.toolong}")
	private String farmerImage;

	@Size(max=500, message = "{farmprofile.farmercontact.toolong}")
	private String farmerContact;

	private boolean enabled;

	private Date createdDate;

	private String createdBy;

}
