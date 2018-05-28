package com.swpc.organicledger.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.swpc.organicledger.emodel.RecordTypeEnum;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "T_FARM_RECORD")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@EntityListeners(AuditingEntityListener.class)
public class FarmRecord {
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

	@Column(name = "RECORD_DATE")
	private Date recordDate;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "RECORD_TYPE", length = 10)
	private RecordTypeEnum recordType;

	@Column(name = "RECORD_DESC", length = 500)
	private String recordDesc;
	
	@Column(name = "RECORD_FACTOR", length = 100)
	private String recordFactor;
	
	@Column(name = "RECORD_AMOUNT")
	private BigDecimal recordAmount;
	
	@Column(name = "RECORD_REMARK", length = 500)
	private String recordRemark;
	
	@Column(name = "IMAGE_RECORD", length = 255)
	private String imageRecord;
	
	@Column(name = "VIDEO_RECORD", length = 255)
	private String videoRecord;
	
	@Column(name = "CREATED_DATE", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdDate;

	@Column(name = "CREATED_BY")
	@CreatedBy
	private String createdBy;

	
}
