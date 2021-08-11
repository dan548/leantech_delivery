package ai.leantech.delivery.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate junkRestTemplate(
            @Value("${junk_base_url}") String junkBaseUrl,
            RestTemplateBuilder builder) {
        return builder.rootUri(junkBaseUrl).build();
    }
}
