package com.swpc.organicledger.model;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "T_AUTHORITY", uniqueConstraints = {@UniqueConstraint(columnNames = {"NAME"})})
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@EntityListeners(AuditingEntityListener.class)
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CREATED_DATE", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdDate;

	@Column(name = "MODIFIED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date modifiedDate;

	@Column(name = "CREATED_BY")
	@CreatedBy
	private String createdBy;

	@Column(name = "MODIFIED_BY")
	@LastModifiedBy
	private String modifiedBy;
	
    @Override
    public String getAuthority() {
        return getName();
    }
}
