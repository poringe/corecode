package com.swpc.organicledger.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.swpc.organicledger.model.Farm;
import com.swpc.organicledger.model.dto.FarmDto;
import com.swpc.organicledger.service.FarmService;

import io.swagger.annotations.Api;

@Api(tags = "Farm")
@RestController
@RequestMapping("/secured/farm")
public class FarmController {

	@Autowired
	private FarmService farmService;

	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<FarmDto> getAll(
			@RequestParam(value = "startRow", required = false, defaultValue = "0") Integer firstResult,
			@RequestParam(value = "endRow", required = false, defaultValue = "100") Integer maxResult)
			throws Exception {
		List<Farm> results = farmService.getAll(firstResult, maxResult);
		if (results.isEmpty()) {
			return new ArrayList<FarmDto>();
		}
		return results.stream().map(farm -> convertToDto(farm)).collect(Collectors.toList());
	}

	@RequestMapping(value = "/byFarmProfileCode/{farmProfileCode}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<FarmDto> getByFarmProfileCode(
			@PathVariable String farmProfileCode,
			@RequestParam(value = "startRow", required = false, defaultValue = "0") Integer firstResult,
			@RequestParam(value = "endRow", required = false, defaultValue = "100") Integer maxResult)
			throws Exception {
		List<Farm> results = farmService.getAll(firstResult, maxResult);
		if (results.isEmpty()) {
			return new ArrayList<FarmDto>();
		}
		return results.stream().map(farm -> convertToDto(farm)).collect(Collectors.toList());
	}

	@RequestMapping(value = "/{farmCode}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody FarmDto get(@PathVariable String farmCode) throws Exception {
		return convertToDto(farmService.get(farmCode));
	}

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody FarmDto create(@Valid @RequestBody FarmDto farm) throws Exception {
		Farm farmCreate = convertToEntity(farm);
		farmService.create(farmCreate);
		return convertToDto(farmCreate);
	}

	@RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public void update(@Valid @RequestBody FarmDto farm) throws Exception {
		farmService.update(convertToEntity(farm));
	}

	@RequestMapping(value = "/{farmCode}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public void delete(@PathVariable String farmCode) throws Exception {
		farmService.delete(farmCode);
	}

	@RequestMapping(value = "/filter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<FarmDto> filter(@RequestParam(value = "searchText", required = false) String searchText,
			@RequestParam(value = "farmProfileCode", required = false, defaultValue = "") String farmProfileCode,
			@RequestParam(value = "startRow", required = false, defaultValue = "0") Integer firstResult,
			@RequestParam(value = "endRow", required = false, defaultValue = "100") Integer maxResult)
			throws Exception {
		List<Farm> results = farmService.filter(searchText, farmProfileCode, firstResult, maxResult);
		if (results.isEmpty()) {
			return new ArrayList<FarmDto>();
		}
		return results.stream().map(farm -> convertToDto(farm)).collect(Collectors.toList());
	}

	private FarmDto convertToDto(Farm farm) {
		if (farm == null) {
			return null;
		}
		FarmDto farmDto = modelMapper.map(farm, FarmDto.class);
		return farmDto;
	}

	private Farm convertToEntity(FarmDto farmDto) {
		if (farmDto == null) {
			return null;
		}
		Farm farm = modelMapper.map(farmDto, Farm.class);
		return farm;
	}
}