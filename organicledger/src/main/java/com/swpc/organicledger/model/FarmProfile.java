package com.swpc.organicledger.model;

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

import com.swpc.organicledger.emodel.OperationType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "T_FARM_PROFILE")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@EntityListeners(AuditingEntityListener.class)
public class FarmProfile {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "FARM_PROFILE_CODE", length = 10)
	private String farmProfileCode;

	@Column(name = "NAME", length = 100)
	private String name;

	@Column(name = "ADDRESS", length = 500)
	private String address;

	@Column(name = "GMAP_ADDRESS", length = 100)
	private String gmapAddress;

	@Column(name = "DESCRIPTION", length = 500)
	private String description;

	@Column(name = "FARMER_NAME", length = 255)
	private String farmerName;

	@Column(name = "FARMER_IMG", length = 255)
	private String farmerImage;

	@Column(name = "FARMER_CONTACT", length = 500)
	private String farmerContact;

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
