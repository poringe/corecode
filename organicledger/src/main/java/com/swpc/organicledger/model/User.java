package com.swpc.organicledger.model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swpc.organicledger.emodel.UserTypeEnum;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "T_USER", uniqueConstraints = { @UniqueConstraint(columnNames = { "USER_NAME", "EMAIL_ADDRESS" }) })
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_NAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "FIRST_NAME", length = 50)
	private String firstName;
    
	@Column(name = "LAST_NAME", length = 50)
	private String lastName;
	
	@Column(name = "EMAIL_ADDRESS", length = 50)
	private String emailAddress;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "USER_TYPE", length = 10)
	private UserTypeEnum userType;
	
	@Column(name = "FARM_PROFILE_CODE", length = 10)
	private String farmProfileCode;
	
    @Column(name = "ACCOUNT_EXPIRED")
    private boolean accountExpired;

    @Column(name = "ACCOUNT_LOCKED")
    private boolean accountLocked;

    @Column(name = "CREDENTIALS_EXPIRED")
    private boolean credentialsExpired;

    @Column(name = "ENABLED")
    private boolean enabled;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "T_USERS_AUTHORITIES", joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID"))
    @OrderBy
    @JsonIgnore
    private Collection<Authority> authorities;

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
    public boolean isAccountNonExpired() {
        return !isAccountExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isAccountLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !isCredentialsExpired();
    }
}
