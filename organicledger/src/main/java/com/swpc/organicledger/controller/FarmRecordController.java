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

import com.swpc.organicledger.model.FarmRecord;
import com.swpc.organicledger.model.dto.FarmRecordDto;
import com.swpc.organicledger.service.FarmRecordService;

import io.swagger.annotations.Api;

@Api(tags = "FarmRecord")
@RestController
@RequestMapping("/secured/farmRecord")
public class FarmRecordController {

	@Autowired
	private FarmRecordService farmRecordService;

	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<FarmRecordDto> getAll(
			@RequestParam(value = "startRow", required = false, defaultValue = "0") Integer firstResult,
			@RequestParam(value = "endRow", required = false, defaultValue = "100") Integer maxResult)
			throws Exception {
		List<FarmRecord> results = farmRecordService.getAll(firstResult, maxResult);
		if (results.isEmpty()) {
			return new ArrayList<FarmRecordDto>();
		}
		return results.stream().map(farmRecord -> convertToDto(farmRecord)).collect(Collectors.toList());
	}

	@RequestMapping(value = "/byFarmingCode/{farmingCode}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<FarmRecordDto> getByFarmingCode(
			@PathVariable String farmingCode,
			@RequestParam(value = "startRow", required = false, defaultValue = "0") Integer firstResult,
			@RequestParam(value = "endRow", required = false, defaultValue = "100") Integer maxResult)
			throws Exception {
		List<FarmRecord> results = farmRecordService.getByFarmingCode(farmingCode, firstResult, maxResult);
		if (results.isEmpty()) {
			return new ArrayList<FarmRecordDto>();
		}
		return results.stream().map(farmRecord -> convertToDto(farmRecord)).collect(Collectors.toList());
	}

	@RequestMapping(value = "/{farmRecordCode}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody FarmRecordDto get(@PathVariable Long id) throws Exception {
		return convertToDto(farmRecordService.get(id));
	}

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody FarmRecordDto create(@Valid @RequestBody FarmRecordDto farmRecord) throws Exception {
		FarmRecord farmRecordCreate = convertToEntity(farmRecord);
		farmRecordService.create(farmRecordCreate);
		return convertToDto(farmRecordCreate);
	}

	private FarmRecordDto convertToDto(FarmRecord farmRecord) {
		if (farmRecord == null) {
			return null;
		}
		FarmRecordDto farmRecordDto = modelMapper.map(farmRecord, FarmRecordDto.class);
		return farmRecordDto;
	}

	private FarmRecord convertToEntity(FarmRecordDto farmRecordDto) {
		if (farmRecordDto == null) {
			return null;
		}
		FarmRecord farmRecord = modelMapper.map(farmRecordDto, FarmRecord.class);
		return farmRecord;
	}
}