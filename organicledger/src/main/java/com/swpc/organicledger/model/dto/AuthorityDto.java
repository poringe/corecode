package com.swpc.organicledger.model.dto;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class AuthorityDto implements Serializable {

    private Long id;

    private String name;

	private Date createdDate;

	private Date modifiedDate;

	private String createdBy;

	private String modifiedBy;

}
