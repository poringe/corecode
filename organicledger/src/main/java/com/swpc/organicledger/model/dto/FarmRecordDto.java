package com.swpc.organicledger.model.dto;

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
import com.swpc.organicledger.model.Unit;
import com.swpc.organicledger.model.UnitMeasures;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FarmRecordDto {

	private String farmProfileCode;

	private String farmCode;
	
	private String farmingCode;

	private Date recordDate;
	
	private RecordTypeEnum recordType;

	private String recordDesc;
	
	private String recordFactor;
	
	private BigDecimal recordAmount;
	
	private String unitName;
	
	private String baseUnitName;
	
	private BigDecimal convertUnit;
	
	private String recordRemark;
	
	private String imageRecord;
	
	private String videoRecord;
	
	private Date createdDate;

	private String createdBy;

	
}
