package org.gmdev.service.bookstore;

import org.gmdev.dao.bookstore.BookRepository;
import org.gmdev.dao.bookstore.ReviewRepository;
import org.gmdev.exception.review.ReviewBadRequestException;
import org.gmdev.model.entity.bookstore.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, BookRepository bookRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
    }

    public Review addOne(Review review) {
        Long bookId = review.getBook().getId();
        if (!bookRepository.existsById(bookId))
            throw new ReviewBadRequestException(String.format("Book with id: %d not present", bookId));

        review.setReviewTimestamp(ZonedDateTime.now(ZoneId.of("Z")));
        return reviewRepository.save(review);
    }

    public Review updateOne(Long id, Review review) {
        return reviewRepository.findById(id)
                .map(reviewInDb -> {
                    reviewInDb.setText(review.getText());
                    return reviewRepository.save(reviewInDb);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Review with id: %d not found", id)));
    }

    public void deleteOne(Long id) {
        if (!reviewRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Review with id: %d not found", id));

        reviewRepository.deleteById(id);
    }
}
