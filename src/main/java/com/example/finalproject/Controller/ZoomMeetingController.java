package com.example.finalproject.Controller;

import com.example.finalproject.Api.ApiResponse;
import com.example.finalproject.Model.ZoomMeeting;
import com.example.finalproject.Service.ZoomMeetingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/zoom")
@RequiredArgsConstructor
public class ZoomMeetingController {
    private final ZoomMeetingService zoomService;

    @GetMapping("/get")
    public ResponseEntity getZooms(){
        return ResponseEntity.status(200).body(zoomService.getAllZoomMeetings());
    }

    @PostMapping("/add/{session_id}")
    public ResponseEntity addZoom(@Valid @RequestBody ZoomMeeting zoom,@PathVariable Integer session_id) {
        zoomService.addZoom(zoom,session_id);
        return ResponseEntity.status(201).body(new ApiResponse("Zoom Added Successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateZoom(@PathVariable Integer id, @Valid @RequestBody ZoomMeeting zoom) {
        zoomService.updateZoom(id, zoom);
        return ResponseEntity.status(200).body(new ApiResponse("Zoom Updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteZoom(@PathVariable Integer id) {
        zoomService.deleteZoom(id);
        return ResponseEntity.status(200).body(new ApiResponse("Zoom Deleted"));
    }

    @PutMapping("/assignZoomToStudent/{zoom_id}/{student_id}")
    public ResponseEntity assignZoomToStudent(@PathVariable Integer zoom_id, @PathVariable Integer student_id){
        zoomService.assignZoomToStudent(zoom_id,student_id);
        return ResponseEntity.status(200).body(new ApiResponse("Zoom meeting Assigned to student successfully"));
    }
}