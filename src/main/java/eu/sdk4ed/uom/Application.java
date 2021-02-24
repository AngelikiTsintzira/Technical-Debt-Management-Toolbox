package eu.sdk4ed.uom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
@SpringBootApplication
@Configuration
//@EnableAutoConfiguration
//@EnableConfigurationProperties
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
