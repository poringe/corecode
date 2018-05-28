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

import com.swpc.organicledger.model.ProductMaster;
import com.swpc.organicledger.model.dto.ProductMasterDto;
import com.swpc.organicledger.service.ProductMasterService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "ProductMaster")
@RestController
@RequestMapping("/secured/productMaster")
public class ProductMasterController {

	@Autowired
	private ProductMasterService productMasterService;

	@Autowired
	private ModelMapper modelMapper;

	@ApiOperation(value = "Get All PRODUCT_MASTER")
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<ProductMasterDto> getAll(
			@RequestParam(value = "startRow", required = false, defaultValue = "0") Integer firstResult,
			@RequestParam(value = "endRow", required = false, defaultValue = "100") Integer maxResult) {
		List<ProductMaster> results = productMasterService.getAll(firstResult, maxResult);
		if (results.isEmpty()) {
			return new ArrayList<ProductMasterDto>();
		}
		return results.stream().map(productMaster -> convertToDto(productMaster)).collect(Collectors.toList());
	}

	@ApiOperation(value = "Get PRODUCT_MASTER by ID")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody ProductMasterDto get(@PathVariable Long id) {
		return convertToDto(productMasterService.get(id));
	}

	@ApiOperation(value = "Search PRODUCT_MASTER")
	@RequestMapping(value = "/filter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<ProductMasterDto> filter(@RequestParam String searchText,
			@RequestParam(value = "startRow", required = false, defaultValue = "0") Integer firstResult,
			@RequestParam(value = "endRow", required = false, defaultValue = "100") Integer maxResult) {
		List<ProductMaster> results = productMasterService.search(searchText, firstResult, maxResult);
		if (results.isEmpty()) {
			return new ArrayList<ProductMasterDto>();
		}
		return results.stream().map(productMaster -> convertToDto(productMaster)).collect(Collectors.toList());
	}

	@ApiOperation(value = "Create PRODUCT_MASTER")
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public ProductMasterDto create(@RequestBody ProductMasterDto productMasterDto) {
		ProductMaster productMasterCreate = convertToEntity(productMasterDto);
		productMasterService.create(productMasterCreate);
		return convertToDto(productMasterCreate);
	}

	@ApiOperation(value = "Modify PRODUCT_MASTER")
	@RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public void update(@RequestBody ProductMasterDto productMasterDto) {
		productMasterService.update(convertToEntity(productMasterDto));
	}

	@ApiOperation(value = "Delete PRODUCT_MASTER")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public void delete(@PathVariable Long id) {
		productMasterService.delete(id);
	}

	private ProductMasterDto convertToDto(ProductMaster productMaster) {
		if (productMaster == null) {
			return null;
		}
		ProductMasterDto productMasterDto = modelMapper.map(productMaster, ProductMasterDto.class);
		return productMasterDto;
	}

	private ProductMaster convertToEntity(ProductMasterDto productMasterDto) {
		if (productMasterDto == null) {
			return null;
		}
		ProductMaster productMaster = modelMapper.map(productMasterDto, ProductMaster.class);
		return productMaster;
	}
}