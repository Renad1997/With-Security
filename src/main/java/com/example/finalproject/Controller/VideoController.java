package com.example.finalproject.Controller;

import com.example.finalproject.Api.ApiResponse;
import com.example.finalproject.Model.Video;
import com.example.finalproject.Service.VideoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/video")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @GetMapping("/get")
    public ResponseEntity getVideos(){
        return ResponseEntity.status(200).body(videoService.getAllVideos());
    }

    @PostMapping("/add/{session_id}")
    public ResponseEntity addVideo(@Valid @RequestBody Video video,@PathVariable Integer session_id) {
        videoService.addVideo(video,session_id);
        return ResponseEntity.status(201).body(new ApiResponse("Video Added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateVideo(@PathVariable Integer id, @Valid @RequestBody Video video) {
        videoService.updateVideo(id, video);
        return ResponseEntity.status(200).body(new ApiResponse("Video Updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteVideo(@PathVariable Integer id) {
        videoService.deleteVideo(id);
        return ResponseEntity.status(200).body(new ApiResponse("Video Deleted"));
    }


    @GetMapping("/video/{id}")
    public ResponseEntity<Video> getVideoById(@PathVariable Integer id) {
        Video video = videoService.getVideoById(id);
        return ResponseEntity.ok(video);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Video>> getVideosByCourseId(@PathVariable Integer courseId) {
        List<Video> videos = videoService.getVideosByCourseId(courseId);
        return ResponseEntity.ok(videos);
    }

    @GetMapping("/priceRange/{minPrice}/{maxPrice}")
    public ResponseEntity<List<Video>> getVideosByPriceRange(
            @PathVariable double minPrice, @PathVariable double maxPrice) {
        List<Video> videos = videoService.getVideosByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(videos);
    }

    @PutMapping("/increasePrice/{id}/{percentage}")
    public ResponseEntity<String> increaseVideoPriceByPercentage(
            @PathVariable Integer id, @PathVariable double percentage) {
        videoService.increaseVideoPriceByPercentage(id, percentage);
        return ResponseEntity.ok("Price increased successfully");
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<Video>> searchVideosByTitle(@PathVariable String keyword) {
        List<Video> videos = videoService.searchVideosByTitle(keyword);
        return ResponseEntity.ok(videos);
    }

    @PutMapping("/decreasePrice/{id}/{percentage}")
    public ResponseEntity<String> decreaseVideoPriceByPercentage(
            @PathVariable Integer id, @PathVariable double percentage) {
        videoService.decreaseVideoPriceByPercentage(id, percentage);
        return ResponseEntity.ok("Price decreased successfully");
    }

    @DeleteMapping("/course/{courseId}")
    public ResponseEntity<String> deleteVideosByCourseId(@PathVariable Integer courseId) {
        videoService.deleteVideosByCourseId(courseId);
        return ResponseEntity.ok("Videos deleted successfully");
    }
}

