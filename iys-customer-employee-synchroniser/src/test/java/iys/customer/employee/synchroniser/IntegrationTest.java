package iys.customer.employee.synchroniser;

import iys.customer.employee.synchroniser.model.Activity;
import iys.customer.employee.synchroniser.model.RegistrationClient;
import iys.customer.employee.synchroniser.model.SpaceCreated;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class IntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    WebTestClient webTestClient;

    String code;

    @BeforeEach
    void setUp() {
        code = UUID.randomUUID().toString();
    }

    @Test
    @Order(1)
    @DisplayName("Create New Customer Space - [POST] /synchronises/create-customer-space")
    void createNewCustomerSpace() {
        webTestClient.post()
                .uri("/synchronises/create-customer-space")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isAccepted();
    }

    @Test
    @Order(3)
    @DisplayName("Unexpected Take Up Space - [POST] /synchronises/take-up")
    void unexpectedTakeUpSpace() {
        webTestClient
                .post()
                .uri("/synchronises/take-up")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(new RegistrationClient(UUID.randomUUID().toString(),
                        OffsetDateTime.now().toString())), RegistrationClient.class)
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    @Order(4)
    @DisplayName("Take Up Space - [POST] /synchronises/take-up")
    void takeUpSpace() {
        webTestClient.post()
                .uri("/synchronises/take-up")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(new RegistrationClient(code,
                        OffsetDateTime.now().toString())), RegistrationClient.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    @Order(5)
    @DisplayName("Not Take Up Previously Occupied Space - [POST] /synchronises/take-up")
    void notTakeUpPreviouslyOccupiedSpace() {
        webTestClient.post()
                .uri("/synchronises/take-up")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(new RegistrationClient(code,
                        OffsetDateTime.now().toString())), RegistrationClient.class)
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

    @Test
    @Order(6)
    @DisplayName("Get Synchronises With Details - [GET] /synchronises")
    void getSynchronisesWithDetails() {
        webTestClient.get()
                .uri("/synchronises")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(SpaceCreated.class);
    }

    @Test
    @Order(7)
    @DisplayName("Get Single Synchronise With Details By Code - [GET] /synchronises/{code}")
    void getCustomerByCode() {
        webTestClient.get()
                .uri("/synchronises/{code}", code)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(
                        response -> Assertions.assertThat(
                                 response.getResponseBody()).isNotNull()
                        );
    }

    @Test
    @Order(8)
    @DisplayName("Delete Customer Space - [DELETE] /synchronises/{code}")
    void deleteCustomerSpace() {
        webTestClient.delete()
                .uri("/synchronises/{code}", code)
                .exchange()
                .expectStatus().isAccepted();
    }

    @Test
    @Order(9)
    @DisplayName("Delete Customer Space That Does Not Exist - [DELETE] /synchronises/{code}")
    void deleteACustomerSpaceThatDoesNotExist() {
        webTestClient.delete()
                .uri("/synchronises/{code}", code)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(10)
    @DisplayName(value = "Take Up Deleted Or Non Existent Space - [POST]  /synchronises/take-up")
    void takeUpDeletedOrNonExistentSpace() {
        webTestClient.post()
                .uri("/synchronises/take-up")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(new RegistrationClient(code,
                        OffsetDateTime.now().toString())), RegistrationClient.class)
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

    @Test
    @Order(11)
    @DisplayName(value = "Obtain Activity Synchronises - [GET] /synchronises/activity")
    void obtainActivity() {
        webTestClient.get()
                .uri("/synchronises")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Activity.class);
    }
}
