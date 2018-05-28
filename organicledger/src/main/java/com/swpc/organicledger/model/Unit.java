package com.swpc.organicledger.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "T_UNIT")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Unit {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME", length = 50)
	private String name;

	@Column(name = "ENABLED")
	private boolean enabled;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "unit", fetch = FetchType.LAZY, orphanRemoval = true)
	@JsonManagedReference
	private Collection<UnitMeasures> unitMeasures;

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
