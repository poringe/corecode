package com.swpc.organicledger.model.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.swpc.organicledger.annotation.StringEnumeration;
import com.swpc.organicledger.emodel.ProductMasterType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class ProductMasterDto {

	private Long id;
	
	@NotEmpty(message = "{productmaster.name.required}")
	@Size(max=50, message = "{productmaster.name.toolong}")
	private String name;

	@NotEmpty(message = "{productmaster.productCategory.required}")
	private ProductCategoryDto productCategoryDto;

	@NotEmpty(message = "{productmaster.unit.required}")
	private UnitDto unitDto;
	
	@Digits(integer=9, fraction=2, message = "{productmaster.defaultPrice.invalid}") 
	private BigDecimal defaultPrice;

	private boolean enabled;

	@Size(max=100, message = "{productmaster.imgpath.toolong}")
	private String imagePath;
	
	@NotEmpty(message = "{productmaster.productMasterType.required}")
	@StringEnumeration(enumClass = ProductMasterType.class)
	@Size(max=10, message = "{productmaster.productMasterType.toolong}")
	private ProductMasterType productMasterType;
	
	private Date createdDate;

	private Date modifiedDate;

	private String createdBy;

	private String modifiedBy;
}
