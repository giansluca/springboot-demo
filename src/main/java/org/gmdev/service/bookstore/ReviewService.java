package org.gmdev.service.bookstore;

import org.gmdev.api.bookstore.model.GetReviewApiRes;
import org.gmdev.dao.bookstore.ReviewRepository;
import org.gmdev.dao.bookstore.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;

    }

    public GetReviewApiRes getOne(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                        String.format("Review with id: %d not found", reviewId)));

        return review.toApiRes();
    }

    public List<GetReviewApiRes> getBookReviews(Long bookId) {
        return reviewRepository.findByBookId(bookId)
                .stream()
                .map(Review::toApiRes)
                .toList();
    }


}
