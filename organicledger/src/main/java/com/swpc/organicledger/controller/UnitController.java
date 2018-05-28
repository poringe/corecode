package com.swpc.organicledger.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

import com.swpc.organicledger.model.Unit;
import com.swpc.organicledger.model.dto.UnitDto;
import com.swpc.organicledger.service.UnitService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Unit")
@RestController
@RequestMapping("/secured/unit")
public class UnitController {

	@Autowired
	private UnitService unitService;

	@Autowired
	private ModelMapper modelMapper;

	@ApiOperation(value = "Get All UNIT")
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<UnitDto> getAll(
			@RequestParam(value = "startRow", required = false, defaultValue = "0") Integer firstResult,
			@RequestParam(value = "endRow", required = false, defaultValue = "100") Integer maxResult) {
		List<Unit> results = unitService.getAll(firstResult, maxResult);
		if (results.isEmpty()) {
			return new ArrayList<UnitDto>();
		}
		return results.stream().map(unit -> convertToDto(unit)).collect(Collectors.toList());
	}

	@ApiOperation(value = "Get UNIT by ID")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody UnitDto get(@PathVariable Long id) {
		return convertToDto(unitService.get(id));
	}

	@ApiOperation(value = "Search UNIT")
	@RequestMapping(value = "/filter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<UnitDto> filter(@RequestParam String searchText,
			@RequestParam(value = "startRow", required = false, defaultValue = "0") Integer firstResult,
			@RequestParam(value = "endRow", required = false, defaultValue = "100") Integer maxResult) {
		List<Unit> results = unitService.search(searchText, firstResult, maxResult);
		if (results.isEmpty()) {
			return new ArrayList<UnitDto>();
		}
		return results.stream().map(unit -> convertToDto(unit)).collect(Collectors.toList());
	}

	@ApiOperation(value = "Create UNIT")
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public UnitDto create(@RequestBody UnitDto unitDto) {
		Unit unitCreate = convertToEntity(unitDto);
		unitService.create(unitCreate);
		return convertToDto(unitCreate);
	}

	@ApiOperation(value = "Modify UNIT")
	@RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public void update(@RequestBody UnitDto unitDto) {
		unitService.update(convertToEntity(unitDto));
	}

	@ApiOperation(value = "Delete UNIT")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public void delete(@PathVariable Long id) {
		unitService.delete(id);
	}

	private UnitDto convertToDto(Unit unit) {
		if (unit == null) {
			return null;
		}
		UnitDto unitDto = modelMapper.map(unit, UnitDto.class);
		return unitDto;
	}

	private Unit convertToEntity(UnitDto unitDto) {
		if (unitDto == null) {
			return null;
		}
		Unit unit = modelMapper.map(unitDto, Unit.class);
		return unit;
	}
}