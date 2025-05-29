package ed.lab.ed1final.controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class E02TrieControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(0)
    void insertHappyPath() {
        webTestClient.post()
                .uri("/trie/apple")
                .exchange()
                .expectStatus().isCreated();

        webTestClient.post()
                .uri("/trie/apple")
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void insertAllUpperCase() {
        webTestClient.post()
                .uri("/trie/APPLE")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void insertSomeLowerCase() {
        webTestClient.post()
                .uri("/trie/ApplE")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(1)
    void wordsEqualTo() {
        webTestClient.get()
                .uri("/trie/apple/count")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.word").isEqualTo("apple")
                .jsonPath("$.wordsEqualTo").isEqualTo(2);
    }

    @Test
    @Order(1)
    void wordsNonEqualTo() {
        webTestClient.get()
                .uri("/trie/ap/count")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.word").isEqualTo("ap")
                .jsonPath("$.wordsEqualTo").isEqualTo(0);
    }

    @Test
    @Order(1)
    void wordsStarting() {
        webTestClient.get()
                .uri("/trie/ap/prefix")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.prefix").isEqualTo("ap")
                .jsonPath("$.wordsStartingWith").isEqualTo(2);
    }

    @Test
    @Order(1)
    void wordsEqualAndStarting() {
        webTestClient.get()
                .uri("/trie/apple/prefix")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.prefix").isEqualTo("apple")
                .jsonPath("$.wordsStartingWith").isEqualTo(2);
    }

    @Test
    @Order(2)
    void delete() {
        webTestClient.delete()
                .uri("/trie/apple")
                .exchange()
                .expectStatus().isNoContent();

        webTestClient.get()
                .uri("/trie/apple/count")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.word").isEqualTo("apple")
                .jsonPath("$.wordsEqualTo").isEqualTo(1);

        webTestClient.get()
                .uri("/trie/ap/prefix")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.prefix").isEqualTo("ap")
                .jsonPath("$.wordsStartingWith").isEqualTo(1);

        webTestClient.delete()
                .uri("/trie/apple")
                .exchange()
                .expectStatus().isNoContent();

        webTestClient.get()
                .uri("/trie/apple/count")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.word").isEqualTo("apple")
                .jsonPath("$.wordsEqualTo").isEqualTo(0);

        webTestClient.get()
                .uri("/trie/ap/prefix")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.prefix").isEqualTo("ap")
                .jsonPath("$.wordsStartingWith").isEqualTo(0);
    }
}