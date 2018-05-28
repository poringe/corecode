package com.swpc.organicledger.model.dto;

import java.io.Serializable;
import java.util.Collection;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class UnitDto implements Serializable {

	private Long id;
	
	@Size(max=100, message = "{unit.name.toolong}")
	private String name;

	private boolean enabled;

	@NotEmpty(message = "{unit.unitMeasures.required}")
	private Collection<UnitMeasuresDto> unitMeasures;

}
