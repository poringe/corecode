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

import com.swpc.organicledger.model.Farming;
import com.swpc.organicledger.model.dto.FarmingDto;
import com.swpc.organicledger.service.FarmingService;

import io.swagger.annotations.Api;

@Api(tags = "Farming")
@RestController
@RequestMapping("/secured/farming")
public class FarmingController {

	@Autowired
	private FarmingService farmingService;

	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<FarmingDto> getAll(
			@RequestParam(value = "startRow", required = false, defaultValue = "0") Integer firstResult,
			@RequestParam(value = "endRow", required = false, defaultValue = "100") Integer maxResult)
			throws Exception {
		List<Farming> results = farmingService.getAll(firstResult, maxResult);
		if (results.isEmpty()) {
			return new ArrayList<FarmingDto>();
		}
		return results.stream().map(farming -> convertToDto(farming)).collect(Collectors.toList());
	}

	@RequestMapping(value = "/byFarmingProfileCode/{farmProfileCode}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<FarmingDto> getByFarmingProfileCode(
			@PathVariable String farmProfileCode,
			@RequestParam(value = "startRow", required = false, defaultValue = "0") Integer firstResult,
			@RequestParam(value = "endRow", required = false, defaultValue = "100") Integer maxResult)
			throws Exception {
		List<Farming> results = farmingService.getAll(firstResult, maxResult);
		if (results.isEmpty()) {
			return new ArrayList<FarmingDto>();
		}
		return results.stream().map(farming -> convertToDto(farming)).collect(Collectors.toList());
	}

	@RequestMapping(value = "/{farmingCode}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody FarmingDto get(@PathVariable String farmingCode) throws Exception {
		return convertToDto(farmingService.get(farmingCode));
	}

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody FarmingDto create(@Valid @RequestBody FarmingDto farming) throws Exception {
		Farming farmingCreate = convertToEntity(farming);
		farmingService.create(farmingCreate);
		return convertToDto(farmingCreate);
	}

	@RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public void update(@Valid @RequestBody FarmingDto farming) throws Exception {
		farmingService.update(convertToEntity(farming));
	}

	@RequestMapping(value = "/{farmingCode}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public void delete(@PathVariable String farmingCode) throws Exception {
		farmingService.delete(farmingCode);
	}

	@RequestMapping(value = "/filter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<FarmingDto> filter(@RequestParam(value = "searchText", required = false) String searchText,
			@RequestParam(value = "farmProfileCode", required = false, defaultValue = "") String farmProfileCode,
			@RequestParam(value = "startRow", required = false, defaultValue = "0") Integer firstResult,
			@RequestParam(value = "endRow", required = false, defaultValue = "100") Integer maxResult)
			throws Exception {
		List<Farming> results = farmingService.filter(searchText, farmProfileCode, firstResult, maxResult);
		if (results.isEmpty()) {
			return new ArrayList<FarmingDto>();
		}
		return results.stream().map(farming -> convertToDto(farming)).collect(Collectors.toList());
	}

	private FarmingDto convertToDto(Farming farming) {
		if (farming == null) {
			return null;
		}
		FarmingDto farmingDto = modelMapper.map(farming, FarmingDto.class);
		return farmingDto;
	}

	private Farming convertToEntity(FarmingDto farmingDto) {
		if (farmingDto == null) {
			return null;
		}
		Farming farming = modelMapper.map(farmingDto, Farming.class);
		return farming;
	}
}