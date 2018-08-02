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
@EqualsAndHashCode(of = "id")
public class ProductCategoryDto  implements Serializable{

	private Long id;

	@NotEmpty(message = "{productcategory.name.required}")
	@Size(max=50, message = "{productcategory.name.toolong}")
	private String name;

	private boolean enabled;
	
	private Date createdDate;

	private Date modifiedDate;

	private String createdBy;

	private String modifiedBy;

}
