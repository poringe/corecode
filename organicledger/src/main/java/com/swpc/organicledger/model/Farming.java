package com.swpc.organicledger.model;

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

import com.swpc.organicledger.emodel.FarmingStatusEnum;
import com.swpc.organicledger.emodel.OperationType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "T_FARMING")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Farming {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "FARM_PROFILE_CODE", length = 10)
	private String farmProfileCode;

	@Column(name = "FARM_CODE", length = 15)
	private String farmCode;
	
	@Column(name = "FARMING_CODE", length = 20)
	private String farmingCode;

	@Column(name = "DESCRIPTION", length = 500)
	private String desc;

	@Column(name = "FARM_DESC", length = 500)
	private String farmDesc;
	
	@ManyToOne(targetEntity = ProductType.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_TYPE_ID")
	private ProductType productType;

	@Enumerated(EnumType.STRING)
	@Column(name = "FARMING_STATUS", length = 10)
	private FarmingStatusEnum farmingStatus;

	@Column(name = "ENABLED")
	private boolean enabled;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "OPERATION_TYPE", length = 10)
	private OperationType operationType;
	
	@Column(name = "CREATED_DATE", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdDate;

	@Column(name = "CREATED_BY")
	@CreatedBy
	private String createdBy;

}
