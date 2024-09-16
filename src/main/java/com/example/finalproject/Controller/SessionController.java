package com.example.finalproject.Controller;

import com.example.finalproject.Api.ApiResponse;
import com.example.finalproject.Model.Session;
import com.example.finalproject.Model.Student;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.finalproject.Service.SessionService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/session")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @GetMapping("/get")
    public ResponseEntity getSessions(){
        return ResponseEntity.status(200).body(sessionService.getAllSessions());
    }

    @PostMapping("/add/{course_id}")
    public ResponseEntity addSession(@RequestBody @Valid Session session,@PathVariable Integer course_id){
        sessionService.addSession(session,course_id);
        return ResponseEntity.status(201).body(new ApiResponse("Session Created successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateSession(@PathVariable Integer id, @RequestBody @Valid Session session){
        sessionService.updateSession(id, session);
        return ResponseEntity.status(200).body(new ApiResponse("Session Updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteSession(@PathVariable Integer id){
        sessionService.deleteSession(id);
        return ResponseEntity.status(200).body(new ApiResponse("Session Deleted"));
    }

    @PutMapping("/assignSessionToTutor/{session_id}/{tutor_id}")
    public ResponseEntity assignSessionToTutor (@PathVariable Integer session_id,@PathVariable Integer tutor_id){
        sessionService.AssignSessionToTutor(session_id,tutor_id);
        return ResponseEntity.status(200).body(new ApiResponse("Session Assigned to Tutor successfully"));
    }

    @PutMapping("/assignSessionToStudent/{session_id}/{student_id}")
    public ResponseEntity assignSessionToStudent (@PathVariable Integer session_id,@PathVariable Integer student_id){
        sessionService.assignSessionToStudent(session_id,student_id);
        return ResponseEntity.status(200).body(new ApiResponse("Session Assigned to student successfully"));
    }


    @PutMapping("/start/{id}")
    public ResponseEntity startSession(@PathVariable Integer id){
        sessionService.startSession(id);
        return ResponseEntity.status(200).body(new ApiResponse("Session Started"));
    }

    @PutMapping("/end/{id}")
    public ResponseEntity endSession(@PathVariable Integer id){
        sessionService.endSession(id);
        return ResponseEntity.status(200).body(new ApiResponse("Session Ended"));
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity cancelSession(@PathVariable Integer id){
        sessionService.cancelSession(id);
        return ResponseEntity.status(200).body(new ApiResponse("Session Cancelled"));
    }

    @PutMapping("/assign/{sessionId}/{studentId}")
    public ResponseEntity<String> assignStudentToSession(@PathVariable Integer sessionId, @PathVariable Integer studentId) {
        sessionService.assignStudentToSession(sessionId, studentId);
        return ResponseEntity.ok("Student assigned to session successfully");
    }

    @PutMapping("/block/{sessionId}/{studentId}")
    public ResponseEntity<String> blockStudentFromSession(@PathVariable Integer sessionId, @PathVariable Integer studentId) {
        sessionService.blockStudentFromSession(sessionId, studentId);
        return ResponseEntity.ok("Student blocked from session successfully");
    }

    @GetMapping("/students/{sessionId}")
    public ResponseEntity<Set<Student>> getStudentsInSession(@PathVariable Integer sessionId) {
        Set<Student> students = sessionService.getStudentsInSession(sessionId);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/maxParticipants/{maxParticipants}")
    public ResponseEntity<List<Session>> getSessionsByMaxParticipants(@PathVariable int maxParticipants) {
        List<Session> sessions = sessionService.getSessionsByMaxParticipants(maxParticipants);
        return ResponseEntity.ok(sessions);
    }
}
