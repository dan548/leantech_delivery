package ai.leantech.delivery.network;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class JunkClient {

   private final RestTemplate restTemplate;

   public JunkClient(RestTemplate restTemplate) {
      this.restTemplate = restTemplate;
   }

   public Junk getSingleJunk(Long id) {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);

      HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

      return this.restTemplate
              .exchange("/comments/{id}", HttpMethod.GET, requestEntity, Junk.class, id)
              .getBody();
   }
}
