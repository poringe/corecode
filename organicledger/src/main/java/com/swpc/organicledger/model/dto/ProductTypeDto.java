package com.swpc.organicledger.model.dto;

import java.io.Serializable;
import java.util.Collection;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class ProductTypeDto  implements Serializable{
	private Long id;

	@NotEmpty(message = "{producttype.name.required}")
	@Size(max=50, message = "{producttype.name.toolong}")
	private String name;

	@Size(max=500, message = "{producttype.description.toolong}")
	private String description;

	@Size(max=100, message = "{producttype.description.toolong}")
	private String imagePath;

	@NotEmpty(message = "{producttype.validDay.required}")
	@Digits(integer=9, fraction=0, message = "{productmaster.validDay.invalid}") 
	private Integer validDay;

	@NotEmpty(message = "{producttype.productCategory.required}")
	private ProductCategoryDto productCategory;

	@NotEmpty(message = "{producttype.productMasters.required}")
	private Collection<ProductMasterDto> productMasters;
	
	private long createdDate;

	private long modifiedDate;

	private String createdBy;

	private String modifiedBy;
}
