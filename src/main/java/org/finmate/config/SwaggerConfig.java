package org.finmate.config;


import io.swagger.models.Response;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private final String API_NAME = "FinMate API";
    private final String API_VERSION = "1.0";
    private final String API_DESCRIPTION = "FinMate API 명세서";

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(API_NAME)
                .description(API_DESCRIPTION)
                .version(API_VERSION)
                .build();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .globalResponseMessage(RequestMethod.GET,defaultResponses())
                .globalResponseMessage(RequestMethod.POST,defaultResponses())
                .globalResponseMessage(RequestMethod.PATCH,defaultResponses())
                .globalResponseMessage(RequestMethod.DELETE,defaultResponses())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private List<ResponseMessage> defaultResponses() {
        return List.of(
                new ResponseMessageBuilder().code(400).message("잘못된 요청").build(),
                new ResponseMessageBuilder().code(401).message("인증 실패").build(),
                new ResponseMessageBuilder().code(403).message("권한 없음").build(),
                new ResponseMessageBuilder().code(404).message("리소스를 찾을 수 없음").build(),
                new ResponseMessageBuilder().code(500).message("서버 오류").build()
        );
    }


}
