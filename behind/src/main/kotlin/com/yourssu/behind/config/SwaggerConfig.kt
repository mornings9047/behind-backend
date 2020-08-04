package com.yourssu.behind.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.ParameterBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.schema.ModelRef
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Parameter
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig : WebMvcConfigurationSupport() {
    private final val HEADER_AUTH = "Authorization"

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
                .title("Behind")
                .description("Behind API")
                .build()
    }

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .groupName("Behind")
                .apiInfo(this.apiInfo())
                .globalOperationParameters(getHeader())
                .select()
                .paths(PathSelectors.ant("/**"))
                .apis(RequestHandlerSelectors.basePackage("com.yourssu.behind.controller"))
                .build()
    }

    /*
        swagger-ui.html이 404에러로 뜨지 않는 에러 해결
        springfox의 swagger2 (2.9.x)를 사용하는 경우 발생하는 현상인데 이럴 경우,
        스프링부트의 Configuration을 통해 ResourceHandler를 오버라이드해서 swagger-ui.html 페이지가 어디에 있는지 위치를 추가해줘야 한다.
        밑의 addResourceHandlers를 오버라이드하지 않고 사용하는 방법은 SpringBoot버젼을 2.0.4.RELEASE를 사용하는 법

     */
    @Override
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/")
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/")
    }

    fun getHeader(): List<Parameter> {
        val parameter = ParameterBuilder()
                .name(HEADER_AUTH)
                .modelRef(ModelRef("string"))
                .parameterType("header")
                .defaultValue(null)
                .required(false)
                .build()
        return listOf(parameter)
    }
}
