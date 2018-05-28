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

import com.swpc.organicledger.emodel.ProductStatusEnum;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "T_PRODUCT_RECORD")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class ProductRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "PRODUCT_CODE")
	private String productCode;

	@Column(name = "PRODUCT_AMOUNT")
	private BigDecimal productAmount;

	@Column(name = "DESCRIPTION", length = 500)
	private String description;

	@Column(name = "REF_ID", length = 20)
	private String refId;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", length = 10)
	private ProductStatusEnum status;

	@Column(name = "FARM_PROFILE_CODE", length = 10)
	private String farmProfileCode;

	@Column(name = "FARM_CODE", length = 15)
	private String farmCode;
	
	@Column(name = "FARMING_CODE", length = 20)
	private String farmingCode;

	@ManyToOne(targetEntity = ProductMaster.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_MASTER_ID")
	private ProductMaster productMaster;

	@Column(name = "EXPIRE_DATE")
	private Date expireDate;

	@Column(name = "IMG_PATH", length = 100)
	private String imagePath;
	
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
