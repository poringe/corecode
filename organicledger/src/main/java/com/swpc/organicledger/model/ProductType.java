package com.swpc.organicledger.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "T_PRODUCT_TYPE")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class ProductType {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME", length = 50)
	private String name;

	@Column(name = "DESCRIPTION", length = 100)
	private String description;

	@Column(name = "IMG_PATH", length = 100)
	private String imagePath;

	@Column(name = "VALID_DAY")
	private Integer validDay;

	@ManyToOne(targetEntity = ProductCategory.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_CATEGORY_ID")
	private ProductCategory productCategory;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "T_PRODUCT_TYPES_PRODUCT_MASTERS", joinColumns = @JoinColumn(name = "PRODUCT_TYPE_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "PRODUCT_MASTER_ID", referencedColumnName = "ID"))
	@OrderBy
	@JsonIgnore
	private Collection<ProductMaster> productMasters;
	
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
