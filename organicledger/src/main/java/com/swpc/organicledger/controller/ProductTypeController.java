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

import com.swpc.organicledger.model.ProductType;
import com.swpc.organicledger.model.dto.ProductTypeDto;
import com.swpc.organicledger.service.ProductTypeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "ProductType")
@RestController
@RequestMapping("/secured/productType")
public class ProductTypeController {

	@Autowired
	private ProductTypeService productTypeService;

	@Autowired
	private ModelMapper modelMapper;

	@ApiOperation(value = "Get All PRODUCT_TYPE")
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<ProductTypeDto> getAll(
			@RequestParam(value = "startRow", required = false, defaultValue = "0") Integer firstResult,
			@RequestParam(value = "endRow", required = false, defaultValue = "100") Integer maxResult) {
		List<ProductType> results = productTypeService.getAll(firstResult, maxResult);
		if (results.isEmpty()) {
			return new ArrayList<ProductTypeDto>();
		}
		return results.stream().map(productType -> convertToDto(productType)).collect(Collectors.toList());
	}

	@ApiOperation(value = "Get PRODUCT_TYPE by ID")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody ProductTypeDto get(@PathVariable Long id) {
		return convertToDto(productTypeService.get(id));
	}

	@ApiOperation(value = "Search PRODUCT_TYPE")
	@RequestMapping(value = "/filter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<ProductTypeDto> filter(@RequestParam String searchText,
			@RequestParam(value = "startRow", required = false, defaultValue = "0") Integer firstResult,
			@RequestParam(value = "endRow", required = false, defaultValue = "100") Integer maxResult) {
		List<ProductType> results = productTypeService.search(searchText, firstResult, maxResult);
		if (results.isEmpty()) {
			return new ArrayList<ProductTypeDto>();
		}
		return results.stream().map(productType -> convertToDto(productType)).collect(Collectors.toList());
	}

	@ApiOperation(value = "Create PRODUCT_TYPE")
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public ProductTypeDto create(@RequestBody ProductTypeDto productTypeDto) {
		ProductType productTypeCreate = convertToEntity(productTypeDto);
		productTypeService.create(productTypeCreate);
		return convertToDto(productTypeCreate);
	}

	@ApiOperation(value = "Modify PRODUCT_TYPE")
	@RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public void update(@RequestBody ProductTypeDto productTypeDto) {
		productTypeService.update(convertToEntity(productTypeDto));
	}

	@ApiOperation(value = "Delete PRODUCT_TYPE")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public void delete(@PathVariable Long id) {
		productTypeService.delete(id);
	}

	private ProductTypeDto convertToDto(ProductType productType) {
		if (productType == null) {
			return null;
		}
		ProductTypeDto productTypeDto = modelMapper.map(productType, ProductTypeDto.class);
		return productTypeDto;
	}

	private ProductType convertToEntity(ProductTypeDto productTypeDto) {
		if (productTypeDto == null) {
			return null;
		}
		ProductType productType = modelMapper.map(productTypeDto, ProductType.class);
		return productType;
	}
}