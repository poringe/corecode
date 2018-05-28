package com.swpc.organicledger.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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

import com.swpc.organicledger.emodel.OperationType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "T_FARM")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Farm {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "FARM_CODE", length = 15)
	private String farmCode;

	@Column(name = "IMG_PATH", length = 100)
	private String imagePath;

	@Column(name = "AREA")
	private BigDecimal area;

	@Column(name = "GMAP_ADDRESS", length = 100)
	private String gmapAddress;
	
	@Column(name = "DESCRIPTION", length = 500)
	private String desc;

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
