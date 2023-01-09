package org.gmdev.api;

import org.gmdev.model.dto.ReviewDto;
import org.gmdev.model.entity.Review;
import org.gmdev.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RequestMapping("api/v1/review")
@Validated
@RestController
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ReviewDto addOne(@NotNull @RequestBody ReviewDto reviewDto) {
        Review newReview = reviewService.addOne(mapper.toEntity(reviewDto));
        return mapper.toDto(newReview);
    }

    @PutMapping(path = "{id}")
    public ReviewDto updateOne(@PathVariable("id") Long id,
                               @NotNull @RequestBody ReviewDto reviewDto) {

        Review updatedReview = reviewService.updateOne(id, mapper.toEntity(reviewDto));
        return mapper.toDto(updatedReview);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "{id}")
    public void deleteOne(@PathVariable("id") Long id) {
        reviewService.deleteOne(id);
    }
}
