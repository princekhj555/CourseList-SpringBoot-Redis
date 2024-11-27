package com.redis.app.Redis.Application.controller;

import com.redis.app.Redis.Application.dao.CourseDao;
import com.redis.app.Redis.Application.model.Course;
import com.redis.app.Redis.Application.service.MainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/")
@CrossOrigin("*")
public class MainController {

    @Autowired
    private MainService mainService;

    @Autowired
    private CourseDao courseDao;

    @GetMapping("/course/{courseId}")
    public Course courseDetails(@PathVariable(name = "courseId") Integer courseId){
        Course response=null;
        try{
            response = courseDao.getRedisCourse(courseId.toString());
            log.info("Value of response from getRedisCourse is : {}",response );
            if(response!=null){
                log.info("Cache Hit :: Getting response from Redis");
                return response;
            }else{
                log.info("Cache Miss :: Getting response from API");
                response = mainService.getCourseDetails(courseId);
                courseDao.saveRedisCourse(response);
            }
        } catch (Exception e) {
            log.error("Exception at courseDetails Controller {} ",e.getMessage());
        }
        return response;
    }
    @GetMapping("/courses")
    public List<Course> allCoursesDetails(){
        List<Course> response=new ArrayList<>();
        List<Object> redisResp;
        try {

            redisResp=courseDao.getAllRedisCourse();

            if(redisResp!=null && !redisResp.isEmpty()){
                log.info("Cache Hit :: Getting response from Redis");
                response= (List<Course>) redisResp.get(0);
            }else {
                log.info("Cache Miss :: Getting response from API");
                response = mainService.getAllCoursesDetails();
                courseDao.saveALlRedisCourse(response);
            }
        } catch (Exception e) {
            log.error("Exception at courseDetails Controller {} ",e.getMessage());
        }
        return response;
    }

}
