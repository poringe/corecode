package com.swpc.organicledger.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.swpc.organicledger.emodel.ProductMasterType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "T_PRODUCT_MASTER")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class ProductMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME", length = 50)
	private String name;

	@ManyToOne(targetEntity = ProductCategory.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_CATEGORY_ID")
	private ProductCategory productCategory;

	@ManyToOne(targetEntity = Unit.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "UNIT_ID")
	private Unit unit;
	
	@Column(name = "DEFAULT_PRICE")
	private BigDecimal defaultPrice;

	@Column(name = "ENABLED")
	private boolean enabled;

	@Column(name = "IMG_PATH", length = 100)
	private String imagePath;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "PRODUCT_MASTER_TYPE", length = 10)
	private ProductMasterType productMasterType;
	
	@Column(name = "CREATED_DATE", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdDate;

	@Column(name = "MODIFIED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date modifiedDate;

	@Column(name = "CREATED_BY")
	@CreatedBy
	private String createdBy;

	@Column(name = "MODIFIED_BY")
	@LastModifiedBy
	private String modifiedBy;
}
