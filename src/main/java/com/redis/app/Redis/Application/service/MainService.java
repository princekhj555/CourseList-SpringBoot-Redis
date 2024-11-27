package com.redis.app.Redis.Application.service;

import com.redis.app.Redis.Application.model.Course;

import java.util.List;

public interface MainService {

    public Course getCourseDetails(Integer courseId);

    public List<Course> getAllCoursesDetails();
}
