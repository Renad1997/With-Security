package com.example.finalproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    private Integer id;// Same as User ID

    @NotEmpty(message = "First name should not be empty!")
    private String firstName;

    @NotEmpty(message = "Last name should not be empty!")
    private String lastName;

    @NotEmpty(message ="Phone number cannot be null")
    @Pattern(regexp = "^(05|0)[0-9]{8}$" ,message = " Phone number Must start with 05, consists of exactly 10 digits")
    @Column(columnDefinition = "varchar(10) not null unique")
    private String phoneNumber;

    //@AssertFalse
    @Column(columnDefinition = "boolean default false")
    private boolean isEnrolled;

    @Column(columnDefinition = "datetime default (current_timestamp)")
    private LocalDate enrollmentDate;


  //Relations

    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;


    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> course;


    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Review> reviews;




//    @OneToMany(mappedBy = "student")
//    @JsonIgnore
//    private List<Session> session;



    @ManyToMany
   // @JsonIgnore
    @JoinTable(
            name = "student_club",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "club_id")
    )
    private List<Club> clubs;


    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "student_used_item",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "usedItem_id")
    )
    private List<UsedItem> usedItems;


//    @OneToMany(mappedBy = "student")
//    private List<Orders> orders;



    @OneToMany(mappedBy ="student",cascade = CascadeType.ALL  )
    private Set<ZoomMeeting> zoomMeeting;


    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL)
    private Set<Document> documents;


    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL)
    private Set<Video> videos;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL)
    private Set<FaceToFace> faceToFace;





}
