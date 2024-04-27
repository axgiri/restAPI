package axgiri.restAPI.Config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfrig {

    @Bean
    public OpenAPI api(){
        return new OpenAPI()
        .servers(List.of(
            new Server().url("http://localhost:8080")))
        .info(
            new Info().title("cats API")
        );
        
    }
}
