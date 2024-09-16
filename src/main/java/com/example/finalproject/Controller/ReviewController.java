package com.example.finalproject.Controller;

import com.example.finalproject.Api.ApiResponse;
import com.example.finalproject.Model.Review;
import com.example.finalproject.Model.User;
import com.example.finalproject.Service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
public class ReviewController {

    private final ReviewService reviewService;


    @GetMapping("/get")
    public ResponseEntity getAllReviews() {
        return ResponseEntity.status(200).body(reviewService.getAllReviews());
    }

    @PostMapping("/add/{student_id}")
    public ResponseEntity addReview(@PathVariable Integer student_id,@Valid @RequestBody Review review) {
        reviewService.addReview(student_id,review);
        return ResponseEntity.status(200).body(new ApiResponse("Review added successfully"));
    }

    @PutMapping("/update/{review_id}")
    public ResponseEntity updateReview(@AuthenticationPrincipal User user,@PathVariable Integer review_id,@Valid @RequestBody Review review) {
        reviewService.updateReview(user.getId(),review_id, review);
        return ResponseEntity.status(200).body(new ApiResponse("Review updated successfully"));
    }

    @DeleteMapping("/delete/{review_id}")
    public ResponseEntity deleteReview(@AuthenticationPrincipal User user,@PathVariable Integer review_id) {
        reviewService.deleteReview(user.getId(),review_id);
        return ResponseEntity.status(200).body(new ApiResponse("Review deleted successfully"));
    }

    @PutMapping("/assignReviewToTutor/{review_id}/{tutor_id}")
    public ResponseEntity assignReviewToTutor (@PathVariable Integer review_id, @PathVariable Integer tutor_id){
        reviewService.assignReviewToTutor(review_id, tutor_id);
        return ResponseEntity.status(200).body(new ApiResponse("Review assigned to tutor successfully!"));
    }

    @PutMapping("/assignReviewToCourse/{student_id}/{review_id}/{course_id}")
    public ResponseEntity assignReviewToCourse (@PathVariable Integer student_id,@PathVariable Integer review_id, @PathVariable Integer course_id){
        reviewService.assignReviewToCourse(student_id,review_id, course_id);
        return ResponseEntity.status(200).body(new ApiResponse("Review assigned to course successfully!"));
    }

}
