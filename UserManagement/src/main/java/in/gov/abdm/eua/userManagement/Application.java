package in.gov.abdm.eua.userManagement;

import in.gov.abdm.eua.userManagement.constants.ConstantsUtils;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
<<<<<<< HEAD
=======
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@OpenAPIDefinition(info = @Info(title = "EUA client", version = "1.0", description = ConstantsUtils.EUA_CLIENT_DESCRIPTION))
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}