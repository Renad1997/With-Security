package com.example.finalproject.Service;

import com.example.finalproject.Api.ApiException;
import com.example.finalproject.Model.Document;
import com.example.finalproject.Model.Session;
import com.example.finalproject.Model.Student;
import com.example.finalproject.Model.ZoomMeeting;
import com.example.finalproject.Repository.DocumentRepository;
import com.example.finalproject.Repository.SessionRepository;
import com.example.finalproject.Repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final SessionRepository sessionRepository;
    private final StudentRepository studentRepository;


    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    //Reema
    // Assign Document to Session >> tutor
    public void addDocument(Document document,Integer session_id) {
        Session session = sessionRepository.findSessionById(session_id);
        if(session == null) {
            throw new ApiException("Session not found");
        }
        document.setSession(session);
        documentRepository.save(document);
    }

    public Document updateDocument(Integer id, Document document) {
        Document d = findDocumentById(id);
        if(d == null) {
            throw new ApiException("Document not found");
        }
        d.setTitle(document.getTitle());
        d.setPrice(document.getPrice());
        return documentRepository.save(d);
    }

    public void deleteDocument(Integer id) {
        Document d = findDocumentById(id);
        if(d == null) {
            throw new ApiException("Document not found");
        }
        documentRepository.deleteById(id);
    }

    //Omar
    public Document findDocumentById(Integer id) {
        Document document = documentRepository.findDocumentById(id);
        if (document == null) {
            throw new ApiException("Document not found");
        }
        return documentRepository.findDocumentById(id);
    }

    //Reema
    //Assign Doc to student >> student
    public void assignDocToStudent (Integer doc_id, Integer student_id) {
        Document doc = documentRepository.findDocumentById(doc_id);
        if(doc == null) {
            throw new ApiException("Document not found");
        }

        Student student = studentRepository.findStudentById(student_id);
        if (student == null) {
            throw  new ApiException("Student not found");
        }
        if (!student.isEnrolled()){
            throw new ApiException("Student is not enrolled, you can't assign this Document!");
        }

        doc.setStudent(student);
        documentRepository.save(doc);
        studentRepository.save(student);
    }





}
