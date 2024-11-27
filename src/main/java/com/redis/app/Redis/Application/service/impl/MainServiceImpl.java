package com.redis.app.Redis.Application.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis.app.Redis.Application.model.Course;
import com.redis.app.Redis.Application.service.MainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MainServiceImpl implements MainService {

    public static  String URL = "https://dummy-json.mock.beeceptor.com/posts";

    @Override
    public Course getCourseDetails(Integer courseId) {

        Course course = new Course();
        try {
            // Step 1: Use RestTemplate to make a GET request
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(URL+"/"+courseId.toString(), String.class);
            // Step 2: Map JSON response to List<Course>
            ObjectMapper objectMapper = new ObjectMapper();
            course = objectMapper.readValue(response.getBody(), new TypeReference<Course>() {});
        } catch (Exception e) {
            log.error("Error fetching Course : {}", e.getMessage());
        }
        return course;
    }

    @Override
    public List<Course> getAllCoursesDetails() {
        List<Course> courseList = new ArrayList<>();
        try {
            // Step 1: Use RestTemplate to make a GET request
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(URL, String.class);
            // Step 2: Map JSON response to List<Course>
            ObjectMapper objectMapper = new ObjectMapper();
            courseList = objectMapper.readValue(response.getBody(), new TypeReference<List<Course>>() {});
        } catch (Exception e) {
            log.error("Error fetching all Courses: {}", e.getMessage());
        }
        return courseList;
    }
}
