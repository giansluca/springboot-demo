package org.gmdev.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gmdev.api.bookstore.model.CreateBookApiReq;
import org.gmdev.api.bookstore.model.GetBookApiRes;
import org.gmdev.api.bookstore.model.UpdateBookApiReq;
import org.gmdev.dao.bookstore.entity.Author;
import org.gmdev.dao.bookstore.entity.Book;
import org.gmdev.service.bookstore.BookstoreTestHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    BookstoreTestHelper bookstoreTestHelper;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mockMvc;

    @AfterEach
    void cleanUp() {
        bookstoreTestHelper.cleanDb();
    }

    @Test
    void itShouldFindOneBook() throws Exception {
        // Given
        List<Book> books = bookstoreTestHelper.getFakeBooksWithAuthors();
        bookstoreTestHelper.saveBookList(books);
        Book book = books.get(0);

        // When
        ResultActions getBookAction = mockMvc.perform(get("/api/v1/book/{bookId}", book.getId()));

        // Then
        getBookAction
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("The blue book"));
    }

    @Test
    void itShouldThrowIfBookNotFound() throws Exception {
        // Given
        List<Book> books = bookstoreTestHelper.getFakeBooksWithAuthors();
        bookstoreTestHelper.saveBookList(books);

        // When
        ResultActions getBookAction = mockMvc.perform(get("/api/v1/book/{bookId}", 99L));

        // Then
        getBookAction
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> assertThat(result).isNotNull())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(ResponseStatusException.class))
                .andExpect(result -> assertThat(result.getResponse().getErrorMessage()).isEqualTo("Book with id: 99 not found"));
    }

    @Test
    void itShouldSearchByTitle() throws Exception {
        // Given
        List<Book> books = bookstoreTestHelper.getFakeBooksWithAuthors();
        bookstoreTestHelper.saveBookList(books);

        // When
        ResultActions searchBookAction = mockMvc.perform(get("/api/v1/book/search").param("title", "Fishing"));

        // Then
        searchBookAction
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Fishing Theory"));
    }

    @Test
    void itShouldInsertNewBook() throws Exception {
        // Given
        Author author = new Author("Zacaria Jumbo", LocalDateTime.now(), LocalDateTime.now());
        bookstoreTestHelper.saveAuthor(author);

        CreateBookApiReq bodyReq = new CreateBookApiReq("The blue book", author.getId(), 280, "AAA-111-BBB");

        // When
        ResultActions saveBookAction = mockMvc.perform(post("/api/v1/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(bodyReq))
        );

        // Then
        MvcResult result = saveBookAction
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response).isNotNull();
    }

    @Test
    void itShouldUpdateBook() throws Exception {
        // Given
        List<Book> books = bookstoreTestHelper.getFakeBooksWithAuthors();
        bookstoreTestHelper.saveBookList(books);
        Book book = books.get(0);
        Long bookId = book.getId();

        UpdateBookApiReq bodyReq = new UpdateBookApiReq("updated-title", 199, null);

        // When
        ResultActions updateBookAction = mockMvc.perform(put("/api/v1/book/{bookId}", bookId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bodyReq))
        );

        // Then
        MvcResult result = updateBookAction
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        GetBookApiRes updatedBook = mapper.readValue(response, GetBookApiRes.class);

        assertThat(updatedBook.getTitle()).isEqualTo("updated-title");
    }

    @Test
    void itShouldDeleteBook() throws Exception {
        // Given
        List<Book> books = bookstoreTestHelper.getFakeBooksWithAuthors();
        bookstoreTestHelper.saveBookList(books);
        Book book = books.get(0);
        Long bookId = book.getId();

        // When
        ResultActions deleteAction = mockMvc.perform(delete("/api/v1/book/{bookId}", bookId));

        // Then
        deleteAction.andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(get("/api/v1/book/{id}", bookId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


}
