package com.swpc.organicledger.model.dto;

import java.io.Serializable;
import java.util.Collection;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swpc.organicledger.annotation.StringEnumeration;
import com.swpc.organicledger.emodel.UserTypeEnum;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "id")
public class UserDto implements Serializable {

	private Long id;

	@NotEmpty(message = "{user.username.required}")
	@Size(max=50, message = "{user.username.toolong}")
	private String username;

	@Size(max=20, message = "{user.password.toolong}")
	private String password;

	@NotEmpty(message = "{user.firstname.required}")
	@Size(max=50, message = "{user.firstname.toolong}")
	private String firstName;

	@NotEmpty(message = "{user.lastname.required}")
	@Size(max=50, message = "{user.lastname.toolong}")
	private String lastName;

	@NotEmpty(message = "{user.email.required}")
	@Size(max=50, message = "{user.email.toolong}")
	private String emailAddress;

	@NotEmpty(message = "{user.usertype.required}")
	@StringEnumeration(enumClass = UserTypeEnum.class)
	@Size(max=10, message = "{user.usertype.toolong}")
	private String userType;

	@Size(max=10, message = "{user.farmcode.toolong}")
	private String farmCode;

	@NotEmpty(message = "{user.authorities.required}")
	private Collection<AuthorityDto> authorities;

	private boolean enabled;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getFarmCode() {
		return farmCode;
	}

	public void setFarmCode(String farmCode) {
		this.farmCode = farmCode;
	}

	public Collection<AuthorityDto> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<AuthorityDto> authorities) {
		this.authorities = authorities;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
