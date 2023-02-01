package org.gmdev.api.bookstore;

import lombok.extern.slf4j.Slf4j;
import org.gmdev.api.bookstore.model.GetReviewApiRes;
import org.gmdev.service.bookstore.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("api/v1/review")
@Validated
@RestController
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/{reviewId}")
    public GetReviewApiRes getOne(@PathVariable Long reviewId) {
        log.info("Incoming call to [ReviewController - getOne]");
        return reviewService.getOne(reviewId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/book/{bookId}")
    public List<GetReviewApiRes> getBookReviews(@PathVariable Long bookId) {
        log.info("Incoming call to [ReviewController - getBookReviews]");
        return reviewService.getBookReviews(bookId);
    }


}
