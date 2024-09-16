package com.example.finalproject.Service;

import com.example.finalproject.Api.ApiException;
import com.example.finalproject.Model.Course;
import com.example.finalproject.Model.Session;
import com.example.finalproject.Model.Student;
import com.example.finalproject.Model.Tutor;
import com.example.finalproject.Repository.CourseRepository;
import com.example.finalproject.Repository.SessionRepository;
import com.example.finalproject.Repository.StudentRepository;
import com.example.finalproject.Repository.TutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final TutorRepository tutorRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;


    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    //Reema
    //assign session to course
    public void addSession(Session session, Integer course_id) {
        Course course = courseRepository.findCourseById(course_id);
        if (course == null) {
            throw  new ApiException("Course not found, you can't add a session with a course that doesn't exist");
        }
        session.setCourse(course);
        course.getSessions().add(session);
        sessionRepository.save(session);
    }


    public Session updateSession(Integer id, Session session) {
        Session s = sessionRepository.findSessionById(id);
        if(session == null) {
            throw new ApiException("Session not found");
        }
       // s.setPrice(session.getPrice());
       // s.setDate(session.getDate());
        s.setLearningMethod(session.getLearningMethod());
        s.setDuration(session.getDuration());
        return sessionRepository.save(s);
    }

    public void deleteSession(Integer id) {
        Session s = sessionRepository.findSessionById(id);
        if(s == null) {
            throw new ApiException("Session not found");
        }
        sessionRepository.deleteById(id);
    }


    //assign session to tutor
    // cancel session
    // block a student
    // max number of students
    // min price of session
    // start a session

    //Reema
    //assign session to tutor
    public void AssignSessionToTutor (Integer session_id,Integer tutor_id) {
        Session session1 = sessionRepository.findSessionById(session_id);
        if(session1 == null) {
            throw new ApiException("Session not found");
        }
        Tutor tutor = tutorRepository.findTutorById(tutor_id);
        if (tutor == null) {
            throw  new ApiException("Tutor not found");
        }
        session1.setTutor(tutor);
        tutor.getSessions().add(session1);
        sessionRepository.save(session1);
    }

//Reema - Omar
    public void assignSessionToStudent (Integer session_id, Integer student_id) {
        Session session1 = sessionRepository.findSessionById(session_id);
        if(session1 == null) {
            throw new ApiException("Session not found");
        }
       Student student = studentRepository.findStudentById(student_id);
        if (student == null) {
            throw  new ApiException("Student not found");
        }
        if (session1.getStudents().size() >= session1.getMaxParticipants()) {
            throw new ApiException("Maximum number of participants reached.");
        }

        session1.getStudents().add(student);
        sessionRepository.save(session1);
    }

    // Omar
    public void startSession(Integer id) {
        Session session = sessionRepository.findSessionById(id);
        if (session == null) {
            throw new ApiException("Session not found");
        }
        if ("PENDING".equals(session.getStatus())) {
            session.setStatus("ACTIVE");
            sessionRepository.save(session);
        } else {
            throw new ApiException("Session cannot be started. Current status: " + session.getStatus());
        }
    }

    // Omar
    public void cancelSession(Integer id) {
        Session session = sessionRepository.findSessionById(id);
        if (session == null) {
            throw new ApiException("Session not found");
        }
        if ("ACTIVE".equals(session.getStatus()) || "PENDING".equals(session.getStatus())) {
            session.setStatus("CANCELED");
            sessionRepository.save(session);
        } else {
            throw new ApiException("Session cannot be canceled. Current status: " + session.getStatus());
        }
        //remove all students
        session.getStudents().clear();
        sessionRepository.save(session);
    }

    // Omar
    // end a session if it's "ACTIVE"
    public void endSession(Integer id) {
        Session session = sessionRepository.findSessionById(id);
        if (session == null) {
            throw new ApiException("Session not found");
        }
        if ("ACTIVE".equals(session.getStatus())) {
            session.setStatus("COMPLETED");
            sessionRepository.save(session);
        } else {
            throw new ApiException("Only active sessions can be completed.");
        }
    }

    // Omar
    // Assign a student to a session if the maximum number of participants hasn't been reached
    public void assignStudentToSession(Integer sessionId, Integer studentId) {
        Session session = sessionRepository.findSessionById(sessionId);
        Student student = studentRepository.findStudentById(studentId);
        if (student == null) {
            throw new ApiException("Student not found");
        }
        if (session == null) {
            throw new ApiException("Session not found");
        }
        if (session.getStudents().size() >= session.getMaxParticipants()) {
            throw new ApiException("Maximum number of participants reached.");
        }
        session.getStudents().add(student);
        sessionRepository.save(session);
    }

    // Omar
    // Remove a student from a session (effectively blocking them from attending)
    public void blockStudentFromSession(Integer sessionId, Integer studentId) {
        Session session = sessionRepository.findSessionById(sessionId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ApiException("Student not found"));
        if (session == null) {
            throw new ApiException("Session not found");
        }
        if (session.getStudents().contains(student)) {
            session.getStudents().remove(student);
            sessionRepository.save(session);
        } else {
            throw new ApiException("The student is not part of this session.");
        }
    }

    // Omar
    public Set<Student> getStudentsInSession(Integer sessionId) {
        Session session = sessionRepository.findSessionById(sessionId);
        if (session == null) {
            throw new ApiException("Session not found");
        }
        return session.getStudents();
    }

    // Omar
    public List<Session> getSessionsByMaxParticipants(int maxParticipants) {
        return sessionRepository.findAllByMaxParticipants(maxParticipants);
    }



}