package com.swpc.organicledger.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FarmDto implements Serializable {

	@Size(max=15, message = "{farm.farmcode.toolong}")
	@Column(name = "FARM_CODE", length = 15)
	private String farmCode;

	@Size(max=10, message = "{farm.farmprofilecode.toolong}")
	private String farmProfileCode;
	
	@Size(max=100, message = "{farm.imgpath.toolong}")
	private String imagePath;

	@Column(name = "AREA")
	private BigDecimal area;

	@Size(max=100, message = "{farm.gmapaddress.toolong}")
	private String gmapAddress;
	
	@Size(max=500, message = "{farm.description.toolong}")
	private String desc;

	private Date createdDate;

	private String createdBy;
}
