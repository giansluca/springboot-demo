package org.gmdev.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.*;
import org.gmdev.api.model.bookstore.BookDetailDto;
import org.gmdev.api.model.bookstore.BookDto;
import org.gmdev.setup.UtilsForTest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.*;

import java.util.HashSet;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BookIntegrationTest {

    @Autowired
    UtilsForTest utils;

    @Autowired
    MockMvc mockMvc;

    @AfterEach
    void tearDown() {
        utils.deleteAllBooks();
    }

    //@Test
    void itShouldSelectBook() throws Exception {
        // Given
        Long bookId = utils.insertBook();

        // When
        ResultActions getBookAction = mockMvc.perform(
                get("/api/v1/book/{id}", bookId));

        // Then
        getBookAction
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Test book")));
    }

    //@Test
    void itShouldSaveBook() throws Exception {
        // Given
        var bookDetail = new BookDetailDto();
        bookDetail.setIsbn("Test book detail");
        bookDetail.setPages(100);

        var book = new BookDto();
        book.setTitle("Test book");
        book.setBookDetail(bookDetail);
        book.setAuthors(new HashSet<>());

        // When
        ResultActions saveBookAction = mockMvc.perform(post("/api/v1/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(utils.objectToJson(book)))
        );

        // Then
        MvcResult response = saveBookAction
                .andExpect(status().isCreated())
                .andReturn();

        String jsonResponse = response.getResponse().getContentAsString();
        ObjectNode jsonObj = new ObjectMapper().readValue(jsonResponse, ObjectNode.class);
        Long savedBookId = jsonObj.get("id").asLong();

        assertThat(savedBookId).isNotNull();
    }

    //@Test
    void itShouldUpdateBook() throws Exception {
        // Given
        // ... a saved book
        Long bookId = utils.insertBook();

        // ... a book update
        var bookDetail = new BookDetailDto();
        bookDetail.setIsbn("Test book detail - updated");
        bookDetail.setPages(100);

        var book = new BookDto();
        book.setTitle("Test book - updated");
        book.setBookDetail(bookDetail);
        book.setAuthors(new HashSet<>());

        // When
        ResultActions updateBookAction = mockMvc.perform(put("/api/v1/book/{id}", bookId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(utils.objectToJson(book)))
        );

        // Then
        MvcResult response = updateBookAction
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = response.getResponse().getContentAsString();
        ObjectNode jsonObj = new ObjectMapper().readValue(jsonResponse, ObjectNode.class);
        String title = jsonObj.get("title").asText();
        String isbn = jsonObj.get("bookDetail").get("isbn").asText();

        assertThat(title).contains("updated");
        assertThat(isbn).contains("updated");
    }

    //@Test
    void itShouldDeleteBook() throws Exception {
        // Given
        // ... a saved book
        Long bookId = utils.insertBook();

        // When
        ResultActions deleteAction = mockMvc.perform(delete("/api/v1/book/{id}", bookId));

        // Then
        // ... status is OK
        deleteAction.andExpect(status().isNoContent());

        // ... book is NOT FOUND
        mockMvc.perform(get("/api/v1/book/{id}", bookId))
                .andExpect(status().isNotFound());
    }

    //@Test
    void itShouldSelectBooKsSearchingByTitle() throws Exception {
        // Given
        // ... some saved book
        utils.insertBook();
        utils.insertBook();
        utils.insertBook();

        String toSearch = "test";

        // When
        ResultActions searchAction = mockMvc.perform(get("/api/v1/book/search?title={text}", toSearch));

        // Then
        // ... status is OK
        MvcResult response = searchAction
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = response.getResponse().getContentAsString();
        ArrayNode jsonArray = new ObjectMapper().readValue(jsonResponse, ArrayNode.class);

        assertThat(jsonArray.size()).isEqualTo(3);
    }


}
