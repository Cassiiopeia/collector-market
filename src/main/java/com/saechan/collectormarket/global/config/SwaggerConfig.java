package com.saechan.collectormarket.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
  @Bean
  public OpenAPI usedItemsApi(){
    return new OpenAPI()
        .info(new Info()
            .version("0.1")
            .title("COLLECTOR-MARKETPLACE")
            .description("SUH SAECHAN"));
  }
}
