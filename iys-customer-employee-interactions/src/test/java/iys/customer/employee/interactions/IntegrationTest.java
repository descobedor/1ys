package iys.customer.employee.interactions;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import iys.customer.employee.interactions.entity.Space;
import iys.customer.employee.interactions.model.CustomerSpace;
import iys.customer.employee.interactions.model.SpaceCreated;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class IntegrationTest {


    static String code;
    static String reference;
    @Autowired
    WebTestClient webTestClient;

    @BeforeAll
    static void setUp() {
        code = UUID.randomUUID().toString();
        reference = UUID.randomUUID().toString();
    }

    @Test
    @Order(1)
    @DisplayName("Create New Customer Space - [POST] /interactions")
    void createNewCustomerSpace() {
        webTestClient.post()
                .uri("/interactions")
                .accept(MediaType.valueOf(IMAGE_PNG_VALUE))
                .body(Mono.just(new CustomerSpace()), CustomerSpace.class)
                .exchange()
                .expectBody()
                .consumeWith(res -> {
                    assertThat(res.getResponseBody()).isNotNull();
                    assertThat(res.getStatus().is2xxSuccessful()).isTrue();
                });
    }

    @Test
    @Order(1)
    @DisplayName("Create New Customer Space with reference code - [POST] /interactions")
    void createNewCustomerSpaceWithReferenceCode() {
        webTestClient.post()
                .uri("/interactions")
                .accept(MediaType.valueOf(IMAGE_PNG_VALUE))
                .body(Mono.just(new CustomerSpace("553M")), CustomerSpace.class)
                .exchange()
                .expectBody()
                .consumeWith(res -> {
                    assertThat(res.getResponseBody()).isNotNull();
                    assertThat(res.getStatus().is2xxSuccessful()).isTrue();
                });
    }

    @Test
    @Order(2)
    @DisplayName("Get all customer spaces with details - [GET] /interactions")
    void getAllCustomerSpacesWithDetails() {
        webTestClient.get()
                .uri("/interactions?page=0&size=30&sortList=createdDate&sortOrder=DESC")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBody()
                .consumeWith(res -> assertThat(res.getResponseBody()).isNotNull())
                .consumeWith(res -> assertThat(res.getStatus()).isEqualTo(HttpStatusCode.valueOf(200)))
                .consumeWith(System.out::println)
                .consumeWith(res -> {
                    assertThat(res).isNotNull();
                    assertThat(res.getResponseBody()).isNotNull();
                    String s = new String(Objects.requireNonNull(res.getResponseBody()));
                    DocumentContext parse = JsonPath.parse(s);
                    JsonPath compile = JsonPath.compile("$['_embedded']['interactionModelList']");
                    net.minidev.json.JSONArray val = parse.read(compile);
                    code = ((LinkedHashMap<?, ?>) val.get(0)).get("code").toString();
                })
                .jsonPath("$['_embedded']['interactionModelList']").exists()
                .jsonPath("$['_embedded']['interactionModelList']").isArray()
                .jsonPath("$['_links']['self']['href']").exists()
                .jsonPath("$['page']['size']").exists()
                .jsonPath("$['page']['size']").isNumber()
                .jsonPath("$['page']['totalElements']").exists()
                .jsonPath("$['page']['totalElements']").isNumber()
                .jsonPath("$['page']['totalPages']").exists()
                .jsonPath("$['page']['totalPages']").isNumber();
    }

    @Test
    @Order(3)
    @DisplayName("Get Customer space with detail - [GET] /interactions/{code}")
    void getCustomerSpaceWithDetail() {
        webTestClient
                .get()
                .uri("/interactions/{code}", code)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBody()
                .consumeWith(System.out::println)
                .consumeWith(result -> assertThat(
                        result.getResponseBody())
                        .isNotNull())
                .consumeWith(result -> assertThat(
                        result.getStatus())
                        .isEqualTo(HttpStatusCode.valueOf(200))
                );
    }


    @Test
    @Order(3)
    @DisplayName("Create order service - [PUT] /interactions/{code}/orders")
    void takeUpSpace() {
        webTestClient.put()
                .uri("/interactions/{code}/orders", code)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isAccepted();
    }

    @Test
    @Order(4)
    @DisplayName("Delete order service - [DELETE] /interactions/{code}/orders")
    void cancelOrdersByCustomer() {
        webTestClient.delete()
                .uri("/interactions/{code}/orders", code)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isAccepted();
    }

    @Test
    @Order(5)
    @DisplayName("Delete order service by employee by any reference - [DELETE] /interactions/{code}/orders/{reference}")
    void cancelOrdersByEmployeeWithAnyReference() {
        webTestClient.delete()
                .uri("/interactions/{code}/orders/{reference}", code, reference)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isAccepted();
    }

    @Test
    @Order(6)
    @DisplayName("Add reference into space - [PUT] /interactions/{code}")
    void getSynchronisesWithDetails() {
        webTestClient.put()
                .uri("/interactions/{code}", code)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(new SpaceCreated(new Space())), SpaceCreated.class)
                .exchange()
                .expectStatus().isAccepted()
                .expectBodyList(SpaceCreated.class);
    }

    @Test
    @Order(8)
    @DisplayName("Can i have the bill? - [POST] /interactions/{code}/bills")
    void ordersBillByCustomer() {
        webTestClient.post()
                .uri("/interactions/{code}/bills", code)
                .exchange()
                .expectStatus().isAccepted();
    }

    @Test
    @Order(10)
    @DisplayName("Delete space - [DELETE] /interactions/{code}")
    void deleteSpace() {
        webTestClient
                .delete()
                .uri("/interactions/{code}", code)
                .exchange()
                .expectStatus()
                .isAccepted();
    }

}
