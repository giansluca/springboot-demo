package org.gmdev.api.bookstore;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/review")
@Validated
@RestController
public class ReviewController {

//    private final ReviewService reviewService;
//
//    @Autowired
//    public ReviewController(ReviewService reviewService) {
//        this.reviewService = reviewService;
//    }
//
//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping
//    public ReviewDto addOne(@NotNull @RequestBody ReviewDto reviewDto) {
//        Review newReview = reviewService.addOne(mapper.toEntity(reviewDto));
//        return mapper.toDto(newReview);
//    }
//
//    @PutMapping(path = "{id}")
//    public ReviewDto updateOne(@PathVariable("id") Long id,
//                               @NotNull @RequestBody ReviewDto reviewDto) {
//
//        Review updatedReview = reviewService.updateOne(id, mapper.toEntity(reviewDto));
//        return mapper.toDto(updatedReview);
//    }
//
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @DeleteMapping(path = "{id}")
//    public void deleteOne(@PathVariable("id") Long id) {
//        reviewService.deleteOne(id);
//    }

}
