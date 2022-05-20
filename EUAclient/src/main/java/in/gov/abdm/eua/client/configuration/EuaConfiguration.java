package in.gov.abdm.eua.client.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
//@EnableWebFlux
public class EuaConfiguration {
    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
