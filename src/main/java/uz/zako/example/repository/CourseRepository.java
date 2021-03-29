package uz.zako.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.zako.example.entity.Course;
import uz.zako.example.payload.CourseRequest;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> getCourseByTeacherId(Long teacherId);
}
