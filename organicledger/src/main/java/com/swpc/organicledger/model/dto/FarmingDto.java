package com.swpc.organicledger.model.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FarmingDto implements Serializable{

	@NotEmpty(message = "{farming.farmprofilecode.required}")
	@Size(max=10, message = "{farming.farmprofilecode.toolong}")
	private String farmProfileCode;

	@NotEmpty(message = "{farming.farmcode.required}")
	@Size(max=15, message = "{farming.farmcode.toolong}")
	private String farmCode;
	
	@Size(max=20, message = "{farming.farmingcode.toolong}")
	private String farmingCode;

	@NotEmpty(message = "{farming.description.required}")
	@Size(max=500, message = "{farming.description.toolong}")
	private String desc;

	private ProductTypeDto productType;

	@NotEmpty(message = "{farming.farmingstatus.required}")
	@Size(max=10, message = "{farming.farmingstatus.toolong}")
	private String farmingStatus;

	private Date createdDate;

	private String createdBy;
	
	private String farmDesc;

}
