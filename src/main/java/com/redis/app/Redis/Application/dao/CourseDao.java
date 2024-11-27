package com.redis.app.Redis.Application.dao;

import com.redis.app.Redis.Application.model.Course;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
public class CourseDao {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate ;

    private static final String KEY="COURSES";

    private static final String KEY_FOR_ALL="ALL_COURSES";

    public Course saveRedisCourse(Course course){
        log.info("Entering into CourseDAO saveRedisCourse");
        try{
            redisTemplate.opsForHash().put(KEY,course.getId().toString(),course);
            redisTemplate.expire(KEY,10, TimeUnit.SECONDS);
        }catch (Exception ex){
            log.error("Exception into CourseDAO saveRedisCourse : {} ",ex.getMessage());
        }finally {
            log.info("Exiting from  CourseDAO saveRedisCourse");
        }
        return course;
    }

    public Course getRedisCourse(String courseId){
        log.info("Entering into CourseDAO getRedisCourse with CourserId: {} ",courseId);
        Course course=null;
        try {
            course = (Course) redisTemplate.opsForHash().get(KEY,courseId);
            if(course!=null){
                //extending expiry
                redisTemplate.expire(KEY, 10, TimeUnit.SECONDS);
            }
            log.info("Value of course  into CourseDAO getRedisCourse {} ",course);
        } catch (Exception e) {
            log.error("Exception in CourseDAO getRedisCourse");
        } finally {
            log.info("Exiting from  CourseDAO getRedisCourse");
        }
        return course;
    }

    public String saveALlRedisCourse(List<Course> courses){
        log.info("Entering into CourseDAO saveALlRedisCourse");
        String response=null;
        try{
            redisTemplate.opsForList().rightPushAll(KEY_FOR_ALL, courses);

            // Set the expiry time for the key to 1 hour
            redisTemplate.expire(KEY_FOR_ALL, 1, TimeUnit.MINUTES);
            response="Saved into redis";
        }catch (Exception ex){
            log.error("Exception into CourseDAO saveALlRedisCourse : {} ",ex.getMessage());
        }finally {
            log.info("Exiting from  CourseDAO saveALlRedisCourse");
        }
        return response;
    }

    public List<Object> getAllRedisCourse(){
        log.info("Entering into CourseDAO getAllRedisCourse with CourserId");

        List<Object> cachedCourses = null;
        try {
            // Fetch the entire list of courses from Redis
            cachedCourses = redisTemplate.opsForList().range(KEY_FOR_ALL, 0, -1);
            if(cachedCourses!=null &&  !cachedCourses.isEmpty()){
                redisTemplate.expire(KEY_FOR_ALL, 1, TimeUnit.MINUTES);
            }

        } catch (Exception e) {
            log.error("Exception in CourseDAO getAllRedisCourse");
        } finally {
            log.info("Exiting from  CourseDAO getAllRedisCourse");
        }
        return cachedCourses;
    }
}
