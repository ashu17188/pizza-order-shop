package org.innovect.assignment.config;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  .apiInfo(apiInfo()).directModelSubstitute(LocalDate.class, String.class).genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(false)
          .select()           
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any())                        
          .build();                                           
    }
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
          .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
          .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    private ApiInfo apiInfo() {
    	Contact contact = new Contact("Ashutosh Shukla", "", "ashutosh.shukla@innovect.com");
    	return new ApiInfo("Api Documentation",
    			"Api Documentation",
    			"1.0",
    			"urn:tos",
    			contact,
    			"Apache 2.0",
    			"http://www.apache.org/licenses/LICENSE-2.0", new ArrayList());
    }

}