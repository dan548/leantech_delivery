package ai.leantech.delivery.controller;

import ai.leantech.delivery.network.WireMockInitializer;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {WireMockInitializer.class})
public class JunkControllerIT {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private WireMockServer wireMockServer;

    @LocalServerPort
    private Integer port;

    @AfterEach
    public void afterEach() {
        this.wireMockServer.resetAll();
    }

    @Autowired
    private MockMvc mockMvc;

    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer("postgres:11.1")
            .withUsername("daniilp")
            .withPassword("password")
            .withDatabaseName("test");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }

    @Test
    public void testGetJunkByIdShouldReturnDataFromClient() throws Exception {
        this.wireMockServer.stubFor(
                WireMock.get(urlPathMatching("/comments/\\d*"))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBody("{\n" +
                                        "  \"postId\": 1,\n" +
                                        "  \"id\": 1,\n" +
                                        "  \"name\": \"id labore ex et quam laborum\",\n" +
                                        "  \"email\": \"Eliseo@gardner.biz\",\n" +
                                        "  \"body\": \"laudantium enim quasi est quidem magnam voluptate ipsam eos\"\n" +
                                        "}"))
        );

        mockMvc.perform(get("http://localhost:" + port + "/api/junk/54"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.postId").value(1L))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("id labore ex et quam laborum"))
                .andExpect(jsonPath("$.email").value("Eliseo@gardner.biz"))
                .andExpect(jsonPath("$.body").value("laudantium enim quasi est quidem magnam voluptate ipsam eos"));
    }

    @Test
    public void testGetJunkByIdShouldPropagateErrorMessageFromClient() throws Exception {
        this.wireMockServer.stubFor(
                WireMock.get(urlPathMatching("/comments/\\d*"))
                        .willReturn(aResponse()
                                .withStatus(403))
        );

        mockMvc.perform(get("http://localhost:" + port + "/api/junk/54"))
                .andExpect(status().isInternalServerError());
    }
}
