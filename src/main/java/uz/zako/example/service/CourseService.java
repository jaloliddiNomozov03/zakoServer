package uz.zako.example.service;

import org.springframework.data.domain.Page;
import uz.zako.example.entity.Course;
import uz.zako.example.model.Result;
import uz.zako.example.payload.CourseRequest;

import java.util.List;

public interface CourseService {
    Result save(CourseRequest courseRequest);
    public Result edit(Long id, CourseRequest courseRequest);
    public Result delete(Long id);
    public Page<Course> findAllWithPage(int page, int size);
    public List<CourseRequest> findAll();
    public CourseRequest findById(Long id);
    List<Course> getCourseByTeacherId(Long teacherId);
}
