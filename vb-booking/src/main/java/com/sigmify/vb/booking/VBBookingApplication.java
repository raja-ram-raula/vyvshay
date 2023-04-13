package com.sigmify.vb.booking;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.cloud.config.client.ConfigClientProperties;
import org.springframework.cloud.config.client.ConfigServicePropertySourceLocator;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;



// TODO: Auto-generated Javadoc
/**
 * The Class AdminApplication.
 */

@SpringBootApplication(scanBasePackages = { "com.sigmify.vb.booking" })
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
@RefreshScope
@EnableScheduling
//@EnableSwagger2 
public class VBBookingApplication {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication vbBookingApplication = new SpringApplication(VBBookingApplication.class);
		vbBookingApplication.addListeners(new ApplicationPidFileWriter("vb-boking.pid"));
		vbBookingApplication.run(args);
	}
	
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(-1);
        return multipartResolver;
    }
    
    @Bean
	@ConditionalOnMissingBean(ConfigServicePropertySourceLocator.class)
	@ConditionalOnProperty(value = "spring.cloud.config.enabled", matchIfMissing = true)
	public ConfigServicePropertySourceLocator configServicePropertySource(ConfigClientProperties properties) {
		ConfigServicePropertySourceLocator locator = new ConfigServicePropertySourceLocator(
				properties);
		return locator;
	}
    
	
	/*
	 * @Bean public Docket productApi() { return new
	 * Docket(DocumentationType.SWAGGER_2) .select()
	 * .apis(RequestHandlerSelectors.basePackage("com.sigmify.vb.booking.endpoints")
	 * ) .build(); }
	 */
	 


}
