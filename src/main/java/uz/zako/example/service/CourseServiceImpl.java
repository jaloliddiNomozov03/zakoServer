package uz.zako.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.zako.example.entity.Course;
import uz.zako.example.model.Result;
import uz.zako.example.payload.CourseRequest;
import uz.zako.example.repository.CourseRepository;

import java.util.ArrayList;
import java.util.List;
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private TeacherService  teacherService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CourseRepository courseRepository;
    @Override
    public Result save(CourseRequest courseRequest) {
        try {
            Course course = new Course();
            course.setAttachment(attachmentService.findByHashId(courseRequest.getImgUrl()));
            course.setTeacher(teacherService.findById(courseRequest.getTeacheId()));
            course.setCategorie(categoryService.findById(courseRequest.getCategoryId()));
            course.setStatus(courseRequest.getStatus());
            course.setCourseTitleUz(courseRequest.getCourseTitleUz());
            course.setCourseTitleRu(courseRequest.getCourseTitleRu());
            course.setCourseDescUz(courseRequest.getCourseDescUz());
            course.setCourseDescRu(courseRequest.getCourseDescRu());
            course.setStartDate(courseRequest.getStartDate());
            course.setLectures(courseRequest.getLectures());
            course.setDuration(courseRequest.getDuration());
            course.setCertification(courseRequest.getCertification());
            course.setPrice(courseRequest.getPrice());
            courseRepository.save(course);
            return new Result(true,"Successfully saved!");
        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false,"Failed! Not saved.");
    }

    @Override
    public Result edit(Long id, CourseRequest courseRequest) {
        try {
            Course course = courseRepository.findById(id).get();
            course.setAttachment(attachmentService.findByHashId(courseRequest.getImgUrl()));
            course.setTeacher(teacherService.findById(courseRequest.getTeacheId()));
            course.setCategorie(categoryService.findById(courseRequest.getCategoryId()));
            course.setStatus(courseRequest.getStatus());
            course.setCourseTitleUz(courseRequest.getCourseTitleUz());
            course.setCourseTitleRu(courseRequest.getCourseTitleRu());
            course.setCourseDescUz(courseRequest.getCourseDescUz());
            course.setCourseDescRu(courseRequest.getCourseDescRu());
            course.setStartDate(courseRequest.getStartDate());
            course.setLectures(courseRequest.getLectures());
            course.setDuration(courseRequest.getDuration());
            course.setCertification(courseRequest.getCertification());
            course.setPrice(courseRequest.getPrice());
            courseRepository.save(course);
            return new Result(true,"Success updated!");
        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false, "Failed, not edited!");
    }

    @Override
    public Result delete(Long id) {
        try {
            courseRepository.deleteById(id);
            return new Result(true, "deleting success");
        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false, "deleting failed");
    }

    @Override
    public Page<Course> findAllWithPage(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("createAt").descending());
            return courseRepository.findAll(pageable);
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public List<CourseRequest> findAll() {
        try {
            List<Course> courses = courseRepository.findAll();
            List<CourseRequest> list = new ArrayList<>();
            for (Course courseRequest : courses) {
                CourseRequest course = new CourseRequest();
                course.setId(courseRequest.getId());
                course.setImgUrl(courseRequest.getAttachment().getHashId());
                course.setTeacheId(courseRequest.getTeacher().getId());
                course.setCategoryId(courseRequest.getCategorie().getId());
                course.setStatus(courseRequest.getStatus());
                course.setCourseTitleUz(courseRequest.getCourseTitleUz());
                course.setCourseTitleRu(courseRequest.getCourseTitleRu());
                course.setCourseDescUz(courseRequest.getCourseDescUz());
                course.setCourseDescRu(courseRequest.getCourseDescRu());
                course.setStartDate(courseRequest.getStartDate());
                course.setLectures(courseRequest.getLectures());
                course.setDuration(courseRequest.getDuration());
                course.setCertification(courseRequest.getCertification());
                course.setPrice(courseRequest.getPrice());
                list.add(course);
            }
            return list;
        }catch (Exception e){
            System.out.println(e);
        }
        return new ArrayList<>();
    }

    @Override
    public CourseRequest findById(Long id) {
        try {
            Course courseRequest = courseRepository.findById(id).get();
            CourseRequest course = new CourseRequest();
            course.setId(courseRequest.getId());
            course.setImgUrl(courseRequest.getAttachment().getHashId());
            course.setTeacheId(courseRequest.getTeacher().getId());
            course.setCategoryId(courseRequest.getCategorie().getId());
            course.setStatus(courseRequest.getStatus());
            course.setCourseTitleUz(courseRequest.getCourseTitleUz());
            course.setCourseTitleRu(courseRequest.getCourseTitleRu());
            course.setCourseDescUz(courseRequest.getCourseDescUz());
            course.setCourseDescRu(courseRequest.getCourseDescRu());
            course.setStartDate(courseRequest.getStartDate());
            course.setLectures(courseRequest.getLectures());
            course.setDuration(courseRequest.getDuration());
            course.setCertification(courseRequest.getCertification());
            course.setPrice(courseRequest.getPrice());
            return course;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public List<Course> getCourseByTeacherId(Long teacherId) {
        return courseRepository.getCourseByTeacherId(teacherId);
    }

}
