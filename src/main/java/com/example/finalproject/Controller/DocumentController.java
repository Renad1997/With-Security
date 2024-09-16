package com.example.finalproject.Controller;

import com.example.finalproject.Api.ApiResponse;
import com.example.finalproject.Model.Document;
import com.example.finalproject.Service.DocumentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping("/get")
    public ResponseEntity getDocuments(){
        return ResponseEntity.status(200).body(documentService.getAllDocuments());
    }

    @PostMapping("/add/{session_id}")
    public ResponseEntity addDocument(@Valid @RequestBody Document document, @PathVariable Integer session_id) {
        documentService.addDocument(document,session_id);
        return ResponseEntity.status(201).body(new ApiResponse("Document Added Successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateDocument(@PathVariable Integer id, @Valid @RequestBody Document document) {
        documentService.updateDocument(id, document);
        return ResponseEntity.status(200).body(new ApiResponse("Document Updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteDocument(@PathVariable Integer id) {
        documentService.deleteDocument(id);
        return ResponseEntity.status(200).body(new ApiResponse("Document Deleted"));
    }

    @PutMapping("/assignDocumentToStudent/{doc_id}/{student_id}")
    public ResponseEntity assignDocumentToStudent(@PathVariable Integer doc_id, @PathVariable Integer student_id){
        documentService.assignDocToStudent(doc_id,student_id);
        return ResponseEntity.status(200).body(new ApiResponse("Documents Assigned to student successfully"));
    }

}