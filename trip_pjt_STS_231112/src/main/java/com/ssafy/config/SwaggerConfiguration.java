package com.ssafy.config;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration  // 스프링 실행시 설정 파일
@EnableSwagger2  // Swagger2를 사용
public class SwaggerConfiguration {
	// Swagger-UI 2.x 확인
	// http://localhost:8080/{your-app-root}/swagger-ui.html
	// Swagger-UI 3.x 확인
	// http://localhost:8080/{your-app-root}/swagger-ui/index.htmlz

	private String version;
	private String title;

	@Bean
	public Docket api() {
		version = "Admin";
        title = "SSAFY Admin API ";
		return new Docket(DocumentationType.SWAGGER_2).consumes(getConsumeContentTypes())
				.produces(getProduceContentTypes()).apiInfo(apiInfo()).groupName(version).select()
				.apis(RequestHandlerSelectors.basePackage("com.ssafy.admin.controller")).paths(regex("/admin/.*"))
				.build().useDefaultResponseMessages(false);
	}

	@Bean
	public Docket apiMember() {
		version = "Member";
        title = "SSAFY Member API ";
		return new Docket(DocumentationType.SWAGGER_2).consumes(getConsumeContentTypes())
				.produces(getProduceContentTypes()).apiInfo(apiInfo()).groupName(version).select()
				.apis(RequestHandlerSelectors.basePackage("com.ssafy.member.controller")).paths(regex("/member/.*"))
				.build().useDefaultResponseMessages(false);
	}
	
	@Bean
	public Docket apiBoard() {
		version = "Board";
        title = "SSAFY Board API "; 
		return new Docket(DocumentationType.SWAGGER_2).consumes(getConsumeContentTypes())
				.produces(getProduceContentTypes()).apiInfo(apiInfo()).groupName(version).select()
				.apis(RequestHandlerSelectors.basePackage("com.ssafy.board.controller")).paths(regex("/board/.*"))
				.build().useDefaultResponseMessages(false);
	}
	
	@Bean
	public Docket apiTrip() {
		version = "Trip";
        title = "SSAFY Trip API "; 
		return new Docket(DocumentationType.SWAGGER_2).consumes(getConsumeContentTypes())
				.produces(getProduceContentTypes()).apiInfo(apiInfo()).groupName(version).select()
				.apis(RequestHandlerSelectors.basePackage("com.ssafy.trip.controller")).paths(regex("/trip/.*"))
				.build().useDefaultResponseMessages(false);
	}

	//////////////////////////////////////////////
	
	private Set<String> getConsumeContentTypes() {
		Set<String> consumes = new HashSet<>();
		consumes.add("application/json;charset=UTF-8");
//      consumes.add("application/xml;charset=UTF-8");
		consumes.add("application/x-www-form-urlencoded");
		return consumes;
	}

	private Set<String> getProduceContentTypes() {
		Set<String> produces = new HashSet<>();
		produces.add("application/json;charset=UTF-8");
		return produces;
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title(title).description(
				"<h3>SSAFY API Reference for Developers</h3>Swagger를 이용한 Board API<br><img src=\"/assets/img/ssafy_logo.png\" width=\"150\">")
				.contact(new Contact("SSAFY", "https://edu.ssafy.com", "ssafy@ssafy.com")).license("SSAFY License")
				.licenseUrl("https://www.ssafy.com/ksp/jsp/swp/etc/swpPrivacy.jsp").version("1.0").build();
	}
}
