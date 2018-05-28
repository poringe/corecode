package com.swpc.organicledger.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "T_UNIT_MEASURES")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class UnitMeasures {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME", length = 50)
	private String name;

	@Column(name = "BASE")
	private boolean base;

	@Column(name = "CONVERT_TO_BASE")
	private BigDecimal convertToBase;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	private Unit unit;

	@Column(name = "CREATED_DATE", nullable = false, updatable = false)
	@CreatedDate
	private long createdDate;

	@Column(name = "MODIFIED_DATE")
	@LastModifiedDate
	private long modifiedDate;

	@Column(name = "CREATED_BY")
	@CreatedBy
	private String createdBy;

	@Column(name = "MODIFIED_BY")
	@LastModifiedBy
	private String modifiedBy;

}
