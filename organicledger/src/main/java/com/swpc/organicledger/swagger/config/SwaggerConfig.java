package com.swpc.organicledger.swagger.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

	@Bean
	public Docket userApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("User API").select()
				.paths(PathSelectors.ant("/secured/user/**")).build().apiInfo(userApiInfo());
	}

	@Bean
	public Docket unitApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("Unit API").select()
				.paths(PathSelectors.ant("/secured/unit/**")).build().apiInfo(unitApiInfo());
	}

	@Bean
	public Docket farmProfileApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("Farm Profile API").select()
				.paths(PathSelectors.ant("/secured/farmProfile/**")).build().apiInfo(farmProfileApiInfo());
	}

	@Bean
	public Docket productCategoryApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("Product Category API").select()
				.paths(PathSelectors.ant("/secured/productCategory/**")).build().apiInfo(productCategoryApiInfo());
	}

	@Bean
	public Docket productMasterApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("Product Master API").select()
				.paths(PathSelectors.ant("/secured/productMaster/**")).build().apiInfo(productMasterApiInfo());
	}

	@Bean
	public Docket productTypeApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("Product Type API").select()
				.paths(PathSelectors.ant("/secured/productType/**")).build().apiInfo(productTypeApiInfo());
	}

	private ApiInfo userApiInfo() {
		return new ApiInfo("User REST API", "User REST API for Organic ledger", "1.0", null, null, null, null,
				Collections.emptyList());
	}

	private ApiInfo unitApiInfo() {
		return new ApiInfo("Unit REST API", "Unit REST API for Organic ledger", "1.0", null, null, null, null,
				Collections.emptyList());
	}

	private ApiInfo farmProfileApiInfo() {
		return new ApiInfo("Farm Profile REST API", "Farm Profile REST API for Organic ledger", "1.0", null, null, null,
				null, Collections.emptyList());
	}

	private ApiInfo productCategoryApiInfo() {
		return new ApiInfo("Product Category REST API", "Product Category REST API for Organic ledger", "1.0", null,
				null, null, null, Collections.emptyList());
	}

	private ApiInfo productMasterApiInfo() {
		return new ApiInfo("Product Master REST API", "Product Master REST API for Organic ledger", "1.0", null, null,
				null, null, Collections.emptyList());
	}

	private ApiInfo productTypeApiInfo() {
		return new ApiInfo("Product Type REST API", "Product Type REST API for Organic ledger", "1.0", null, null, null,
				null, Collections.emptyList());
	}
}