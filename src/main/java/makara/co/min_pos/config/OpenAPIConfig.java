package makara.co.min_pos.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MINI-POS API")
                        .version("1.0")
                        .description(""))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Server")
                )
                );
    }
}
