package in.gov.abdm.eua.userManagement;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableEurekaClient
@OpenAPIDefinition(info = @Info(title = "eua client", version = "1.0", description = "This is a reference application for EUA client"))
public class Application {

	public static void main(String[] args) {
		 SpringApplication.run(Application.class, args);
	}
}