package in.gov.abdm.eua.client;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "EUA API", version = "1.0", description = "Reference API for EUA"))
public class Application {

	public static void main(String[] args) {

		ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
	}
}