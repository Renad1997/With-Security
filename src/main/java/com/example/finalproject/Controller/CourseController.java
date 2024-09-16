package com.example.finalproject.Controller;

import com.example.finalproject.Api.ApiResponse;
import com.example.finalproject.Model.Course;
import com.example.finalproject.Model.Student;
import com.example.finalproject.Model.User;
import com.example.finalproject.Service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/get")
    public ResponseEntity getAllCourses(){
        return ResponseEntity.status(200).body(courseService.getAllCourses());
    }

    @PostMapping("/add/{tutor_id}")
    public ResponseEntity addCourse(@Valid @RequestBody Course course, @PathVariable Integer tutor_id){
        courseService.addCourse(course, tutor_id);
        return ResponseEntity.status(200).body(new ApiResponse("Course added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateCourse(@PathVariable Integer id,@Valid @RequestBody Course course){
        courseService.updateCourse(course, id);
        return ResponseEntity.status(200).body(new ApiResponse("Course updated successfully!"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCourse(@PathVariable Integer id){
        courseService.deleteCourse(id);
        return ResponseEntity.status(200).body(new ApiResponse("Course deleted successfully!"));
    }

    @GetMapping("/courseFilter/{minPrice}/{maxPrice}")
    public ResponseEntity CourseFilter(@PathVariable double minPrice ,@PathVariable double maxPrice){
        return ResponseEntity.status(200).body(courseService.courseFilter(minPrice,maxPrice));
    }


    @GetMapping("/findCoursesByLearningMethod/{learningMethod}")
    public ResponseEntity findCoursesByLearningMethod (@PathVariable String learningMethod){
        return ResponseEntity.status(200).body(courseService.findCoursesByLearningMethod(learningMethod));
    }


    /*Renad*/
    @GetMapping("/get/course/{course_id}")
    public ResponseEntity enrolledStudentsWithCourse(@AuthenticationPrincipal User user, @PathVariable Integer course_id){
        List<Student> students= courseService.enrolledStudentsWithCourse(user.getId(), course_id);
        return ResponseEntity.status(200).body(students);
    }
    /*Renad*/
    @GetMapping("/get/tutor/{tutor_id}")
    public ResponseEntity enrolledStudentsWithTutor(@AuthenticationPrincipal User user,@PathVariable Integer tutor_id){
        List<Student> students= courseService.enrolledStudentsWithTutor(user.getId(),tutor_id);
        return ResponseEntity.status(200).body(students);
    }
    /*Renad*/
    @GetMapping("/get/subject/{subject}")
    public ResponseEntity searchCourse(@AuthenticationPrincipal User user,@PathVariable String subject){
        return ResponseEntity.status(200).body(courseService.searchCourse(user.getId(),subject));
    }
    /*Renad*/
    @GetMapping("/get/details/{course_id}")
    public ResponseEntity getCourseDetails(@AuthenticationPrincipal User user,@PathVariable Integer course_id){
        return ResponseEntity.status(200).body(courseService.getCourseDetails(user.getId(),course_id));
    }

    /*Renad*/
    @GetMapping("/get/course/reviews/{course_id}/")
    public ResponseEntity getCourseReviews(@AuthenticationPrincipal User user,@PathVariable Integer course_id){
        return ResponseEntity.status(200).body(courseService.getCourseReviews(user.getId(),course_id));
    }



}
