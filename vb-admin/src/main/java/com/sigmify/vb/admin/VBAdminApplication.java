package com.sigmify.vb.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// TODO: Auto-generated Javadoc
/**
 * The Class AdminApplication.
 */

@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
@EnableSwagger2
@SpringBootApplication(scanBasePackages = { "com.sigmify.vb.admin" })
public class VBAdminApplication {
	public static final String PID_FILE="vb-admin.pid";

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication adminApplication = new SpringApplication(VBAdminApplication.class);
		adminApplication.addListeners(new ApplicationPidFileWriter(PID_FILE));
		adminApplication.run(args);
	}
	
	
	@Bean
    public Docket productApi() {
    	return new Docket(DocumentationType.SWAGGER_2)
    			    .select()
    			    .apis(RequestHandlerSelectors.basePackage("com.sigmify.vb.admin.endpoints"))
    			    .build();
    }

}
