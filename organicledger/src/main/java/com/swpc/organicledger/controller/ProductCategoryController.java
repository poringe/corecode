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

import com.swpc.organicledger.model.ProductCategory;
import com.swpc.organicledger.model.dto.ProductCategoryDto;
import com.swpc.organicledger.service.ProductCategoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "ProductCategory")
@RestController
@RequestMapping("/secured/productCategory")
public class ProductCategoryController {

	@Autowired
	private ProductCategoryService productCategoryService;

	@Autowired
	private ModelMapper modelMapper;

	@ApiOperation(value = "Get All PRODUCT_CATEGORY")
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<ProductCategoryDto> getAll(
			@RequestParam(value = "startRow", required = false, defaultValue = "0") Integer firstResult,
			@RequestParam(value = "endRow", required = false, defaultValue = "100") Integer maxResult) {
		List<ProductCategory> results = productCategoryService.getAll(firstResult, maxResult);
		if (results.isEmpty()) {
			return new ArrayList<ProductCategoryDto>();
		}
		return results.stream().map(productCategory -> convertToDto(productCategory)).collect(Collectors.toList());
	}

	@ApiOperation(value = "Get PRODUCT_CATEGORY by ID")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody ProductCategoryDto get(@PathVariable Long id) {
		return convertToDto(productCategoryService.get(id));
	}

	@ApiOperation(value = "Search PRODUCT_CATEGORY")
	@RequestMapping(value = "/filter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<ProductCategoryDto> filter(@RequestParam String searchText,
			@RequestParam(value = "startRow", required = false, defaultValue = "0") Integer firstResult,
			@RequestParam(value = "endRow", required = false, defaultValue = "100") Integer maxResult) {
		List<ProductCategory> results = productCategoryService.search(searchText, firstResult, maxResult);
		if (results.isEmpty()) {
			return new ArrayList<ProductCategoryDto>();
		}
		return results.stream().map(productCategory -> convertToDto(productCategory)).collect(Collectors.toList());
	}

	@ApiOperation(value = "Create PRODUCT_CATEGORY")
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public ProductCategoryDto create(@RequestBody ProductCategoryDto productCategoryDto) {
		ProductCategory productCategoryCreate = convertToEntity(productCategoryDto);
		productCategoryService.create(productCategoryCreate);
		return convertToDto(productCategoryCreate);
	}

	@ApiOperation(value = "Modify PRODUCT_CATEGORY")
	@RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public void update(@RequestBody ProductCategoryDto productCategoryDto) {
		productCategoryService.update(convertToEntity(productCategoryDto));
	}

	@ApiOperation(value = "Delete PRODUCT_CATEGORY")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public void delete(@PathVariable Long id) {
		productCategoryService.delete(id);
	}

	private ProductCategoryDto convertToDto(ProductCategory productCategory) {
		if (productCategory == null) {
			return null;
		}
		ProductCategoryDto productCategoryDto = modelMapper.map(productCategory, ProductCategoryDto.class);
		return productCategoryDto;
	}

	private ProductCategory convertToEntity(ProductCategoryDto productCategoryDto) {
		if (productCategoryDto == null) {
			return null;
		}
		ProductCategory productCategory = modelMapper.map(productCategoryDto, ProductCategory.class);
		return productCategory;
	}
}