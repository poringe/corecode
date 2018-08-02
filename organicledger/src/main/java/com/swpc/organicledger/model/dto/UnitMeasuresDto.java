package com.swpc.organicledger.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class UnitMeasuresDto  implements Serializable{

	private Long id;

	@NotEmpty(message = "{unitMeasures.name.required}")
	private String name;

	@NotEmpty(message = "{unitMeasures.base.required}")
	private boolean base;

	@NotEmpty(message = "{unitMeasures.convertToBase.required}")
	private BigDecimal convertToBase;

	private long createdDate;

	private long modifiedDate;

	private String createdBy;

	private String modifiedBy;

}
