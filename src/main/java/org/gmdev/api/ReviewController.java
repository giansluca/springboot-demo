package org.gmdev.api;

import org.gmdev.model.dto.ReviewDto;
import org.gmdev.model.entity.Review;
import org.gmdev.model.mapper.entitymapper.ReviewMapper;
import org.gmdev.service.ReviewService;
import org.gmdev.validator.ReviewAddGroup;
import org.gmdev.validator.ReviewUpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RequestMapping("api/v1/review")
@RestController
public class ReviewController {

    private final ReviewMapper mapper;
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewMapper mapper, ReviewService reviewService) {
        this.mapper = mapper;
        this.reviewService = reviewService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ReviewDto addOne(@Validated(ReviewAddGroup.class) @NotNull @RequestBody ReviewDto reviewDto) {
        Review newReview = reviewService.addOne(mapper.toEntity(reviewDto));
        return mapper.toDto(newReview);
    }

    @PutMapping(path = "{id}")
    public ReviewDto updateOne(@PathVariable("id") Long id,
                               @Validated(ReviewUpdateGroup.class) @NotNull @RequestBody ReviewDto reviewDto) {

        Review updatedReview = reviewService.updateOne(id, mapper.toEntity(reviewDto));
        return mapper.toDto(updatedReview);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "{id}")
    public void deleteOne(@PathVariable("id") Long id) {
        reviewService.deleteOne(id);
    }
}
