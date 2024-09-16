package com.example.finalproject.Service;

import com.example.finalproject.Api.ApiException;
import com.example.finalproject.Model.*;

import com.example.finalproject.Repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final TutorRepository tutorRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final AuthRepository authRepository;

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }
    //Student
    public void addReview(Integer student_id,Review review) {
        Student student=studentRepository.findStudentById(student_id);
        if(student==null) {
            throw new ApiException("Student not found");
        }
        review.setDateCreated(LocalDate.now()); //edit
        review.setStudent(student);
        reviewRepository.save(review);
        student.getReviews().add(review);
        studentRepository.save(student);
    }

    public void updateReview(Integer auth_id,Integer review_id,Review review) {
        Student student=studentRepository.findStudentById(auth_id);
        if(student==null) {
            throw new ApiException("Student not found");
        }
        Review oldReview=reviewRepository.findReviewById(review_id);
        if(oldReview==null){
            throw new ApiException("Review not found");

        }else if(oldReview.getStudent().getId()!=auth_id){
            throw new ApiException("sorry you dont have authority");
        }

        oldReview.setComment(review.getComment());
        oldReview.setRating(review.getRating());
        oldReview.setDateCreated(LocalDate.now());
        oldReview.setStudent(student);
        reviewRepository.save(oldReview);
    }

    public void deleteReview(Integer auth_id,Integer review_id) {
        Student student=studentRepository.findStudentById(auth_id);
        if(student==null) {
            throw new ApiException("Student not found");
        }
        Review oldReview=reviewRepository.findReviewById(review_id);
        if(oldReview==null){
            throw new ApiException("Review not found");

        }else if(oldReview.getStudent().getId()!=auth_id){
            throw new ApiException("sorry you dont have authority");
        }
        reviewRepository.delete(oldReview);
    }

    //Reema
    public void assignReviewToTutor(Integer review_id, Integer tutor_id) {
        Review review1=reviewRepository.findReviewById(review_id);
        Tutor tutor1=tutorRepository.findTutorById(tutor_id);
        if(review1==null){
            throw new ApiException("Review not found");
        }
        if(tutor1==null){
            throw new ApiException("Tutor not found");
        }
        review1.setTutor(tutor1);
        review1.setDateCreated(LocalDate.now());
        reviewRepository.save(review1);
    }

    //Reema
    public void assignReviewToCourse(Integer student_id,Integer review_id, Integer course_id) {
        Student student1=studentRepository.findStudentById(student_id);
        if(student1==null){
            throw new ApiException("Student not found");
        }
        if(!student1.isEnrolled()){
            throw new ApiException("Student is not enrolled in this course ");
        }
        Review review1=reviewRepository.findReviewById(review_id);
        Course course1=courseRepository.findCourseById(course_id);
        if(review1==null){
            throw new ApiException("Review not found");
        }
        if(course1==null){
            throw new ApiException("Course not found");
        }
        review1.setCourse(course1);
        review1.setDateCreated(LocalDate.now());
        reviewRepository.save(review1);
    }




}
