package com.example.finalproject.Service;

import com.example.finalproject.Api.ApiException;
import com.example.finalproject.Model.Course;
import com.example.finalproject.Model.Document;
import com.example.finalproject.Model.Session;
import com.example.finalproject.Model.Video;
import com.example.finalproject.Repository.CourseRepository;
import com.example.finalproject.Repository.SessionRepository;
import com.example.finalproject.Repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final SessionRepository sessionRepository;
    private final CourseRepository courseRepository;


    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }

    //Reema
   //Assign video to session
    public void addVideo(Video video ,Integer session_id) {
        Session session = sessionRepository.findSessionById(session_id);
        if(session == null) {
            throw new ApiException("Session not found");
        }
        video.setSession(session);
        videoRepository.save(video);
    }

    public Video updateVideo(Integer id, Video video) {
        Video v = videoRepository.findVideoById(id);
        if(video == null) {
            throw new ApiException("Video not found");
        }
        v.setTitle(video.getTitle());
        v.setDescription(video.getDescription());
        v.setPrice(video.getPrice());
        return videoRepository.save(v);
    }

    public void deleteVideo(Integer id) {
        Video v = videoRepository.findVideoById(id);
        if(v == null) {
            throw new ApiException("Video not found");
        }
        videoRepository.deleteById(id);
    }


    //Omar
    public Video getVideoById(Integer id) {
        return videoRepository.findById(id)
                .orElseThrow(() -> new ApiException("Video not found"));
    }

    //Omar
    public List<Video> getVideosByCourseId(Integer courseId) {
        Course course = courseRepository.findCourseById(courseId);
        if (course == null) {
            throw new ApiException("Course not found");
        }
        return videoRepository.findAllByCourse(course);
    }

    //Omar
    public List<Video> getVideosByPriceRange(double minPrice, double maxPrice) {
        return videoRepository.findAllByPriceBetween(minPrice, maxPrice);
    }

    //Omar
    // Get videos by title containing a certain keyword (case-insensitive search)
    public List<Video> searchVideosByTitle(String keyword) {
        return videoRepository.findAllByTitleContainingIgnoreCase(keyword);
    }

    //Omar
    // Increase video price by a percentage
    public void increaseVideoPriceByPercentage(Integer id, double percentage) {
        Video video = videoRepository.findVideoById(id);
        if (video == null) {
            throw new ApiException("Video not found");
        }
        double newPrice = video.getPrice() + (video.getPrice() * (percentage / 100));
        video.setPrice(newPrice);
        videoRepository.save(video);
    }

    //Omar
    // Decrease video price by a percentage
    public void decreaseVideoPriceByPercentage(Integer id, double percentage) {
        Video video = videoRepository.findVideoById(id);
        if (video == null) {
            throw new ApiException("Video not found");
        }
        double newPrice = video.getPrice() - (video.getPrice() * (percentage / 100));
        if (newPrice < 0) {
            throw new ApiException("Price cannot be negative");
        }
        video.setPrice(newPrice);
        videoRepository.save(video);
    }

    //Omar
    // delete all videos by course ID
    public void deleteVideosByCourseId(Integer courseId) {
        Course course = courseRepository.findCourseById(courseId);
        if (course == null) {
            throw new ApiException("Course not found");
        }
        List<Video> videos = videoRepository.findAllByCourse(course);
        if (videos.isEmpty()) {
            throw new ApiException("No videos found for the course");
        }
        videoRepository.deleteAll(videos);
    }

}
