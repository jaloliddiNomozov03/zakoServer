package uz.zako.example.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.zako.example.entity.*;
import uz.zako.example.model.Result;
import uz.zako.example.payload.CourseRequest;
import uz.zako.example.service.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/client")
public class ClientController {
    private StudentService studentService;

    private AttachmentService attachmentService;

    private TeacherService teacherService;

    private UserService userService;

    private CourseService courseService;

    private BlogService blogService;

    private CategoryService categoryService;

    @Autowired
    public ClientController(StudentService studentService,
                           AttachmentService attachmentService,
                           TeacherService teacherService,
                           UserService userService,
                           CourseService courseService,
                           BlogService blogService,
                           CategoryService categoryService){
        this.studentService=studentService;
        this.attachmentService=attachmentService;
        this.teacherService=teacherService;
        this.userService=userService;
        this.courseService=courseService;
        this.blogService=blogService;
        this.categoryService=categoryService;
    }
    @Value("${upload.path}")
    String uploadPath;

    //region StudentController
    @ApiOperation(value = "Student yaratish")

    @GetMapping("/students")
    public Page<Student> students(@RequestParam int page, @RequestParam int size){
        return studentService.findAll(page, size);
    }

    @GetMapping("/student/{id}")
    public Student getStudentById(@PathVariable Long id){
        return  studentService.findById(id);
    }
    //endregion



    @GetMapping("/file/prewiev/{hashids}")
    public  ResponseEntity<InputStreamResource> downloadToServer(@PathVariable String hashids, HttpServletResponse response) throws IOException {
        return attachmentService.downloadToServer(hashids, response);
    }
    //endregion

    //region UserController
    @PostMapping("/user/save")
    public ResponseEntity saveUser(@RequestBody User user){
        return ResponseEntity.ok(userService.addUser(user));
    }
    @GetMapping("/users")
    public ResponseEntity getUsers(@RequestParam int page, @RequestParam int size){
        return ResponseEntity.ok(userService.findAllWithPage(page, size));
    }
    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.deleteUser(id));
    }
    //endregion

    //region BlogController
    @GetMapping("/blogs")
    public ResponseEntity<Page<Blog>> findAllPage(@RequestParam int page, @RequestParam int size){
        return ResponseEntity.ok(blogService.getAll(page, size));
    }
    @GetMapping("/blog/{id}")
    public ResponseEntity<Blog> findByIdBlog(@PathVariable Long id){
        return ResponseEntity.ok(blogService.getById(id));
    }
    // blog Controller
    @GetMapping("/blog/getAll")
    public ResponseEntity<List<Blog>> getAllBlogByType(@RequestParam String status){
        return new ResponseEntity<>(blogService.getAllByBlogTypeAndStatus("ACTIVE",status),HttpStatus.OK);
    }
    //endregion

    //region TeacherController
    @GetMapping("/teacher/getAll")
    public ResponseEntity<Page<Teacher>> getAllTeachers(@RequestParam int page, @RequestParam int size){
        Page<Teacher> teachers = teacherService.findAll(page, size);
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }
    @GetMapping("/teacher/findAll")
    public ResponseEntity<List<Teacher>> getAllTeachers(){
        return ResponseEntity.ok(teacherService.findAllTeachers());
    }
    @GetMapping("/teacher/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable Long id){
        Teacher teacher = teacherService.findById(id);
        return  new ResponseEntity<>(teacher, HttpStatus.OK);
    }
    //endregion

    //region CategoryController
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> findAllCategory(){
        return ResponseEntity.ok(categoryService.findAll());
    }
    //endregion

    //region CourseController
    @GetMapping("/course")
    public ResponseEntity<Page<Course>> getAllCourses(@RequestParam int page, @RequestParam int size){
        Page<Course> courses = courseService.findAllWithPage(page, size);
        return  new ResponseEntity<>(courses,HttpStatus.OK);
    }
    @GetMapping("/course/{id}")
    public ResponseEntity<CourseRequest> getCourseById(@PathVariable Long id){
        CourseRequest courseRequest = courseService.findById(id);
        return  new ResponseEntity<>(courseRequest, HttpStatus.OK);
    }
    @PostMapping("/course/add")
    public ResponseEntity<Result>  addCourse(@RequestBody CourseRequest courseRequest){
        Result result = courseService.save(courseRequest);
        if (result.isSuccess()){
            return new ResponseEntity<>(result,HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(result,HttpStatus.EXPECTATION_FAILED);
        }
    }
    //author Jaloliddin Nomozov
    @PutMapping("/course/edit/{id}")
    public ResponseEntity<Result> editCourse(@PathVariable(name = "id") Long id,@RequestBody CourseRequest courseRequest){
        Result result = courseService.edit(id,courseRequest);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @DeleteMapping("/course/delete/{id}")
    public ResponseEntity<Result> deleteCurse(@PathVariable Long id){
        Result result = courseService.delete(id);
        return  new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/course/teachers/{id}")
    public ResponseEntity<List<Course>> getCourseTeacherBiId(@PathVariable Long id){
        List<Course> courses= courseService.getCourseByTeacherId(id);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    //endregion
}
