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
import com.swpc.organicledger.model.FarmProfile;
import com.swpc.organicledger.model.dto.AuthorityDto;
import com.swpc.organicledger.model.dto.FarmProfileDto;
import com.swpc.organicledger.service.FarmProfileService;

import io.swagger.annotations.Api;

@Api(tags = "FarmProfile")
@RestController
@RequestMapping("/secured/farmProfile")
public class FarmProfileController {

	@Autowired
	private FarmProfileService farmProfileService;

	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<FarmProfileDto> getAll(
			@RequestParam(value = "startRow", required = false, defaultValue = "0") Integer firstResult,
			@RequestParam(value = "endRow", required = false, defaultValue = "100") Integer maxResult)
			throws Exception {
		List<FarmProfile> results = farmProfileService.getAll(firstResult, maxResult);
		if (results.isEmpty()) {
			return new ArrayList<FarmProfileDto>();
		}
		return results.stream().map(farmProfile -> convertToDto(farmProfile)).collect(Collectors.toList());
	}

	@RequestMapping(value = "/{farmCode}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody FarmProfileDto get(@PathVariable String farmCode) throws Exception {
		return convertToDto(farmProfileService.get(farmCode));
	}

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody FarmProfileDto create(@Valid @RequestBody FarmProfileDto farmProfile) throws Exception {
		FarmProfile farmProfileCreate = convertToEntity(farmProfile);
		farmProfileService.create(farmProfileCreate);
		return convertToDto(farmProfileCreate);
	}

	@RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public void update(@Valid @RequestBody FarmProfileDto farmProfile) throws Exception {
		farmProfileService.update(convertToEntity(farmProfile));
	}

	@RequestMapping(value = "/{farmCode}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public void delete(@PathVariable String farmCode) throws Exception {
		farmProfileService.delete(farmCode);
	}

	@RequestMapping(value = "/filter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<FarmProfileDto> filter(@RequestParam String searchText,
			@RequestParam(value = "startRow", required = false, defaultValue = "0") Integer firstResult,
			@RequestParam(value = "endRow", required = false, defaultValue = "100") Integer maxResult)
			throws Exception {
		List<FarmProfile> results = farmProfileService.filter(searchText, firstResult, maxResult);
		if (results.isEmpty()) {
			return new ArrayList<FarmProfileDto>();
		}
		return results.stream().map(farmProfile -> convertToDto(farmProfile)).collect(Collectors.toList());
	}

	private FarmProfileDto convertToDto(FarmProfile farmProfile) {
		if (farmProfile == null) {
			return null;
		}
		FarmProfileDto farmProfileDto = modelMapper.map(farmProfile, FarmProfileDto.class);
		return farmProfileDto;
	}

	private FarmProfile convertToEntity(FarmProfileDto farmProfileDto) {
		if (farmProfileDto == null) {
			return null;
		}
		FarmProfile farmProfile = modelMapper.map(farmProfileDto, FarmProfile.class);
		return farmProfile;
	}
}