package org.gmdev.setup;

import org.gmdev.dao.CarRepository;
import org.gmdev.dao.bookstore.BookRepository;
import org.gmdev.model.entity.Car;
import org.gmdev.model.entity.bookstore.Book;
import org.gmdev.model.entity.bookstore.BookDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
public class UtilsForTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CarRepository carRepository;

    public Long insertBook() {
        LocalDateTime timestamp = LocalDateTime.now();

        var bookDetail = new BookDetail();
        bookDetail.setIsbn("Test book detail");
        bookDetail.setPages(100);
        bookDetail.setCreatedAt(timestamp);

        var book = new Book();
        book.setTitle("Test book");
        book.setBookDetail(bookDetail);
        book.setAuthors(new ArrayList<>());
        book.setCreatedAt(timestamp);

        bookDetail.setBook(book);

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


}
