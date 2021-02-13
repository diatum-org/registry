package org.coredb.registry;

import java.io.*;

import org.slf4j.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.domain.*;
import org.springframework.boot.builder.*;
import org.springframework.boot.web.support.*;
import org.springframework.context.annotation.*;
import org.springframework.context.support.*;
import org.springframework.core.io.*;
import org.springframework.core.io.support.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.transaction.annotation.*;
import org.springframework.data.jpa.repository.config.*;

@SpringBootApplication
@EnableScheduling

@EnableJpaRepositories(basePackages = { "org.coredb.registry.jpa.repository" })
@ComponentScan(basePackages = { "org.coredb.registry" })
@EntityScan(basePackages = { "org.coredb.registry" })

public class Application extends SpringBootServletInitializer {
	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() {
    Resource[] resources;
		PropertySourcesPlaceholderConfigurer propertyConfigurer = new PropertySourcesPlaceholderConfigurer();
    resources = new Resource[] { 
      (new PathMatchingResourcePatternResolver()).getResource("classpath:config.properties"),
    };
    propertyConfigurer.setLocations(resources);
		return propertyConfigurer;
	}
}

