package org.gmdev.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gmdev.model.entity.Book;
import org.gmdev.dao.BookRepository;
import org.gmdev.dao.CarRepository;
import org.gmdev.model.entity.BookDetail;
import org.gmdev.model.entity.Car;
import org.gmdev.model.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.fail;

@Component
public class UtilsForTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CarRepository carRepository;

    public Long insertBook() {
        ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("Z"));

        var bookDetail = new BookDetail();
        bookDetail.setIsbn("Test book detail");
        bookDetail.setPages(100);
        bookDetail.setBookDetailTimestamp(timestamp);

        var book = new Book();
        book.setTitle("Test book");
        book.setBookDetail(bookDetail);
        book.setAuthors(new HashSet<>());
        book.setBookTimestamp(timestamp);

        bookDetail.setBook(book);

        var savedBook = bookRepository.save(book);
        return savedBook.getId();
    }

    public Long insertBookWithReview() {
        ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("Z"));

        var bookDetail = new BookDetail();
        bookDetail.setIsbn("Test book detail");
        bookDetail.setPages(100);
        bookDetail.setBookDetailTimestamp(timestamp);

        var review = new Review();
        review.setText("Very - Good");
        review.setReviewTimestamp(timestamp);

        var book = new Book();
        book.setTitle("Test book");
        book.setBookDetail(bookDetail);
        book.setReviews(new HashSet<>(Collections.singletonList(review)));
        book.setAuthors(new HashSet<>());
        book.setBookTimestamp(timestamp);

        bookDetail.setBook(book);
        review.setBook(book);

        var savedBook = bookRepository.save(book);
        return savedBook.getId();
    }

    public String insertCar() {
        var car = new Car();
        car.setName("Test car");

        var savedCar = carRepository.save(car);
        return savedCar.getId();
    }

    public void deleteAllBooks() {
        bookRepository.deleteAll();
    }

    public void deleteAllCars() {
        carRepository.deleteAll();
    }

    public String objectToJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            fail("Failed to convert object to json");
            return null;
        }
    }
}
