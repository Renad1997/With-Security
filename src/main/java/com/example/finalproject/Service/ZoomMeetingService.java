package com.example.finalproject.Service;

import com.example.finalproject.Api.ApiException;
import com.example.finalproject.Model.Session;
import com.example.finalproject.Model.Student;
import com.example.finalproject.Model.ZoomMeeting;
import com.example.finalproject.Repository.SessionRepository;
import com.example.finalproject.Repository.StudentRepository;
import com.example.finalproject.Repository.ZoomMeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ZoomMeetingService {

    private final ZoomMeetingRepository zoomRepository;
    private final SessionRepository sessionRepository;
    private final StudentRepository studentRepository;


    public List<ZoomMeeting> getAllZoomMeetings() {
        return zoomRepository.findAll();
    }

    //Reema
    // Assign Zoom meeting to Session >> authorized by tutor
    public void addZoom(ZoomMeeting zoom,Integer session_id ) {
        Session session = sessionRepository.findSessionById(session_id);
        if(session == null) {
            throw new ApiException("Session not found");
        }
        zoom.setSession(session);

        zoomRepository.save(zoom);
    }

    public ZoomMeeting updateZoom(Integer id, ZoomMeeting zoom) {
        ZoomMeeting z = zoomRepository.findZoomMeetingByMeetingId(id);
        if(zoom == null) {
            throw new ApiException("Zoom meeting not found");
        }
        z.setPrice(zoom.getPrice());
        z.setUrl(zoom.getUrl());
        z.setMeetingDate(zoom.getMeetingDate());
        return zoomRepository.save(z);
    }

    public void deleteZoom(Integer id) {
        ZoomMeeting z = zoomRepository.findZoomMeetingByMeetingId(id);
        if (z == null) {
            throw new ApiException("Zoom meeting not found");
        }
        zoomRepository.deleteById(id);
    }

    //Reema
    //Assign zoom to student >> student
    public void assignZoomToStudent (Integer zoom_id, Integer student_id) {
        ZoomMeeting zoom = zoomRepository.findZoomMeetingByMeetingId(zoom_id);
        if(zoom == null) {
            throw new ApiException("Zoom meeting not found");
        }
        Student student = studentRepository.findStudentById(student_id);
        if (student == null) {
            throw  new ApiException("Student not found");
        }
        if (!student.isEnrolled()){
            throw new ApiException("Student is not enrolled, you can't assign this zoom!");
        }
        zoom.setStudent(student);
        zoomRepository.save(zoom);
    }

}
