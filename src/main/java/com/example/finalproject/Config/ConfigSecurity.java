package com.example.finalproject.Config;

import org.springframework.context.annotation.Bean;
import com.example.finalproject.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfigSecurity {

    private final MyUserDetailsService myUserDetailsService;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/register","/api/v1/auth/get").permitAll()
                .requestMatchers("/api/v1/student/register",
                        "/api/v1/student/get").permitAll()

                .requestMatchers("/api/v1/auth/get/users/role/{role}").hasAuthority("ADMIN")

                .requestMatchers("/api/v1/review/get").permitAll()
                .requestMatchers("/api/v1/review/add/{student_id}",
                        "api/v1/review/update/{review_id}",
                        "/api/v1/review/delete/{review_id}").hasAuthority("STUDENT")

                .requestMatchers("/api/v1/club/get").permitAll()
                .requestMatchers("/api/v1/club/add/{studentId}",
                        "/api/v1/club/update/{club_id}",
                        "/api/v1/club/delete/{club_id}").hasAuthority("STUDENT")
                .requestMatchers("/api/v1/club/get/club/{name}").hasAuthority("STUDENT")
                .requestMatchers("/api/v1/club/get/details/{club_id}").hasAuthority("STUDENT")
                .requestMatchers("/api/v1/club/students/{club_id}").hasAuthority("STUDENT")
                .requestMatchers("/api/v1/club/capacity/{capacity}").hasAuthority("STUDENT")

                .requestMatchers("/api/v1/usedItem/get").permitAll()
                .requestMatchers("/api/v1/usedItem/add",
                        "/api/v1/usedItem/update/{usedItem_id}",
                        "/api/v1/usedItem/delete/{usedItem_id}").hasAuthority("USER")

                .requestMatchers("/api/v1/orders/get").permitAll()
                .requestMatchers("/api/v1/orders/add",
                        "/api/v1/orders/update/{orders_id}",
                        "/api/v1/orders/delete/{orders_id}").permitAll()
                .requestMatchers("/api/v1/orders/get/order/{status}").hasAuthority("USER")
                .requestMatchers("/api/v1/orders/changeStatus/{order_id}/{status}").hasAuthority("USER")


                .requestMatchers("/api/v1/exam/get").permitAll()
                .requestMatchers("/api/v1/exam/add",
                        "/api/v1/exam/update/{exam_id}",
                        "/api/v1/exam/delete/{exam_id}").hasAuthority("TUTOR")

                .requestMatchers("/api/v1/course/get/course/{course_id}").hasAuthority("TUTOR")
                .requestMatchers("/api/v1/course/get/tutor/{tutor_id}").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/course/get/subject/{subject}").hasAuthority("STUDENT")
                .requestMatchers("/api/v1/course/get/details/{course_id}").hasAuthority("STUDENT")
                .requestMatchers("/api/v1/course/get/course/reviews/{course_id}").permitAll()

                .requestMatchers("/api/v1/tutor/get/name/{firstName}").hasAuthority("STUDENT")
                .requestMatchers("/api/v1/tutor/get/tutor/reviews/{tutor_id}").hasAuthority("STUDENT")
                .requestMatchers("/api/v1/tutor/get/avg/{tutor_id}").hasAuthority("STUDENT")
                .requestMatchers("/api/v1/tutor/get/tutor/products/{tutor_id}").permitAll()
                .requestMatchers("/api/v1/tutor/get/tutors/{gpa}").hasAuthority("STUDENT")
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/auth/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();

    }
}

