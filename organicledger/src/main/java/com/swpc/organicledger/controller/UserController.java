package com.swpc.organicledger.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.swpc.organicledger.model.Authority;
import com.swpc.organicledger.model.User;
import com.swpc.organicledger.model.dto.AuthorityDto;
import com.swpc.organicledger.model.dto.UserDto;
import com.swpc.organicledger.service.UserService;

import io.swagger.annotations.Api;

@Api(tags = "User")
@RestController
@RequestMapping("/secured/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<UserDto> getAll(
			@RequestParam(value = "startRow", required = false, defaultValue = "0") Integer firstResult,
			@RequestParam(value = "endRow", required = false, defaultValue = "100") Integer maxResult)
			throws Exception {
		List<User> results = userService.getAll(firstResult, maxResult);
		if (results.isEmpty()) {
			return new ArrayList<UserDto>();
		}
		return results.stream().map(user -> convertToDto(user)).collect(Collectors.toList());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody UserDto get(@PathVariable Long id) throws Exception {
		return convertToDto(userService.get(id));
	}

	@RequestMapping(value = "/username/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody UserDto getByUsername(@PathVariable String username) throws Exception {
		return convertToDto(userService.loadUserByUsername(username));
	}
	
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody UserDto create(@Valid @RequestBody UserDto user) throws Exception {
		User userCreate = convertToEntity(user);
		userService.create(userCreate);
		return convertToDto(userCreate);
	}

	@RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public void update(@Valid @RequestBody UserDto user) throws Exception {
		userService.update(convertToEntity(user));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public void delete(@PathVariable Long id) throws Exception {
		userService.delete(id);
	}

	@RequestMapping(value = "/filter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<UserDto> filter(@RequestParam String searchText,
			@RequestParam(value = "startRow", required = false, defaultValue = "0") Integer firstResult,
			@RequestParam(value = "endRow", required = false, defaultValue = "100") Integer maxResult)
			throws Exception {
		List<User> results = userService.filter(searchText, firstResult, maxResult);
		if (results.isEmpty()) {
			return new ArrayList<UserDto>();
		}
		return results.stream().map(user -> convertToDto(user)).collect(Collectors.toList());
	}

	private UserDto convertToDto(User user) {
		if (user == null) {
			return null;
		}
		UserDto userDto = modelMapper.map(user, UserDto.class);
		userDto.setAuthorities(user.getAuthorities().stream()
				.map(authority -> modelMapper.map(authority, AuthorityDto.class)).collect(Collectors.toList()));
		return userDto;
	}

	private User convertToEntity(UserDto userDto) {
		if (userDto == null) {
			return null;
		}
		User user = modelMapper.map(userDto, User.class);
		user.setAuthorities(userDto.getAuthorities().stream()
				.map(authority -> modelMapper.map(authority, Authority.class)).collect(Collectors.toList()));
		return user;
	}
}