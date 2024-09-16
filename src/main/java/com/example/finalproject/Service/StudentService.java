package com.example.finalproject.Service;

import com.example.finalproject.Api.ApiException;
import com.example.finalproject.DTO.StudentDTO;
import com.example.finalproject.Model.Course;
import com.example.finalproject.Model.Student;
import com.example.finalproject.Model.User;
import com.example.finalproject.Repository.AuthRepository;
import com.example.finalproject.Repository.CourseRepository;
import com.example.finalproject.Repository.StudentRepository;
import com.example.finalproject.Repository.TutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final AuthRepository authRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;


    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void studentRegister(StudentDTO studentDTO) {
        User user = new User();

       // user.setName(studentDTO.getName());
        user.setUsername(studentDTO.getUsername());
        user.setPassword(studentDTO.getPassword());
       // user.setPassword(new BCryptPasswordEncoder().encode(studentDTO.getPassword()));
        user.setEmail(studentDTO.getEmail());
        user.setRole("STUDENT");
        //user.setRole(studentDTO.getRole());
        user.setAge(studentDTO.getAge());
        user.setGender(studentDTO.getGender());
        user.setEducation_level(studentDTO.getEducation_level());
        user.setDateOfBirth(studentDTO.getDateOfBirth());
        user.setRegistrationDate(LocalDate.now());
        user.setUpdatedAt(LocalDate.now());



        Student student = new Student();
        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setPhoneNumber(studentDTO.getPhoneNumber());
         // student.setEnrolled(studentDTO.isEnrolled());
        //student.setEnrollmentDate(LocalDate.now());


        student.setUser(user);
        user.setStudent(student);

        studentRepository.save(student);
        authRepository.save(user);
    }


    public void updateStudent (StudentDTO studentDTO ,Integer auth_id){
        User user = authRepository.findUserById(auth_id);
        if(user == null){
            throw new ApiException("Student id not found");
        }
        user.setUsername(studentDTO.getUsername());
        user.setPassword(studentDTO.getPassword());
        // user.setPassword(new BCryptPasswordEncoder().encode(studentDTO.getPassword()));
        user.setEmail(studentDTO.getEmail());
        user.setEmail(studentDTO.getEmail());
        user.setAge(studentDTO.getAge());
        user.setGender(studentDTO.getGender());
        user.setEducation_level(studentDTO.getEducation_level());
        user.setDateOfBirth(studentDTO.getDateOfBirth());
        user.setUpdatedAt(LocalDate.now());

        user.getStudent().setFirstName(studentDTO.getFirstName());
        user.getStudent().setLastName(studentDTO.getLastName());
        user.getStudent().setPhoneNumber(studentDTO.getPhoneNumber());
       // user.getStudent().setEnrolled(studentDTO.isEnrolled());

        user.getStudent().setUser(user);

        authRepository.save(user);
    }

    public void deleteStudent(Integer auth_id){
        User user = authRepository.findUserById(auth_id);
        Student student =  studentRepository.findStudentById(auth_id);

        if(user.getId() != auth_id){
            throw new ApiException("Student id doesn't match");
        }
        authRepository.delete(user);
        studentRepository.delete(student);
    }

    //Reema
    // Enroll a student in a course and set enrolled to true
public void studentEnrollment( Integer student_id , Integer course_id){
        Student student = studentRepository.findStudentById(student_id);
        if (student == null){
            throw new ApiException("Student with id  "+ student_id+ " not found");
        }

    Course course = courseRepository.findCourseById(course_id);
        if (course == null){
            throw new ApiException("Course with id  "+ course_id+ " not found");
        }

        if (student.getCourse().contains(course)){
            throw new ApiException("Student already enrolled to this course!");
        }

        student.getCourse().add(course);
        course.getStudents().add(student);
        student.setEnrolled(true);
        student.setEnrollmentDate(LocalDate.now());

        studentRepository.save(student);
        courseRepository.save(course);
}


    //Reema
    public List<Course> getMostPopularCourses() {
        List<Course> popularCourses = courseRepository.findMostPopularCourse();

        if (popularCourses.isEmpty()) {
            throw  new ApiException("No popular courses found");
        }
        return popularCourses;
    }





}
