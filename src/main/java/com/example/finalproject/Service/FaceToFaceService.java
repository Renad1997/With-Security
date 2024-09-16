package com.example.finalproject.Service;

import com.example.finalproject.Api.ApiException;
import com.example.finalproject.Model.Document;
import com.example.finalproject.Model.FaceToFace;
import com.example.finalproject.Model.Session;
import com.example.finalproject.Repository.FaceToFaceRepository;
import com.example.finalproject.Repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FaceToFaceService {

    private final FaceToFaceRepository faceRepository;
    private final SessionRepository sessionRepository;

    public List<FaceToFace> getAllFaceMeeting() {
        return faceRepository.findAll();
    }

    //Reema
    // Assign FaceToFaceMeeting to Session >> tutor
    public void addFaceMeeting(FaceToFace face, Integer session_id) {
        Session session = sessionRepository.findSessionById(session_id);
        if(session == null) {
            throw new ApiException("Session not found");
        }
        face.setSession(session);
        faceRepository.save(face);
    }

    public FaceToFace updateFaceMeeting(Integer id, FaceToFace face) {
        FaceToFace f = faceRepository.findFaceToFaceById(id);
        if(face == null) {
            throw new ApiException("Face to face meeting not found");
        }
        f.setLocation(face.getLocation());
        f.setPrice(face.getPrice());
        return faceRepository.save(f);
    }

    public void deleteFaceMeeting(Integer id) {
        FaceToFace f = faceRepository.findFaceToFaceById(id);
        if(f == null) {
            throw new ApiException("Face to face meeting not found");
        }
        faceRepository.deleteById(id);
    }

    public FaceToFace findFaceById(Integer id) {
        FaceToFace face = faceRepository.findFaceToFaceById(id);
        if (face == null) {
            throw new ApiException("Face to face meeting not found");
        }
        return faceRepository.findFaceToFaceById(id);
    }

}

