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
import org.springframework.web.multipart.MultipartFile;
import uz.zako.example.entity.*;
import uz.zako.example.model.Result;
import uz.zako.example.payload.BlogReq;
import uz.zako.example.payload.CategoryRequest;
import uz.zako.example.payload.CourseRequest;
import uz.zako.example.payload.UserReq;
import uz.zako.example.service.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.List;

@RestController
//@CrossOrigin(origins="http://localhost:4200")
//@CrossOrigin(origins="http://localhost:3000")
@CrossOrigin("*")
@RequestMapping("/api/admin")
public class AdminController {

    private StudentService studentService;

    private AttachmentService attachmentService;

    private TeacherService teacherService;

    private UserService userService;

    private CourseService courseService;

    private BlogService blogService;

    private CategoryService categoryService;
    @Autowired
    public AdminController(StudentService studentService,
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
    @PostMapping("/student/save")
    public Student saveStudent(@RequestBody UserReq userReq){
        return studentService.save(userReq);
    }

    @PutMapping("/student/edit/{id}")
    public Student editStudent(@PathVariable Long id,@RequestBody UserReq userReq){
        return studentService.edit(id, userReq);
    }

    @GetMapping("/students")
    public Page<Student> students(@RequestParam int page, @RequestParam int size){
        return studentService.findAll(page, size);
    }

    @DeleteMapping("/student/delete/{id}")
    public Result deleteStudent(@PathVariable Long id){
        return studentService.delete(id);
    }

    @GetMapping("/student/{id}")
    public Student getStudentById(@PathVariable Long id){
        return  studentService.findById(id);
    }
    //endregion

    //region FileController
    @PostMapping("/file")
    public ResponseEntity<Attachment> addAttach(@RequestParam(name = "file") MultipartFile multipartFile){
       return ResponseEntity.ok(attachmentService.save(multipartFile));
    }

    @GetMapping("/file/getAll")
    public ResponseEntity<Page<Attachment>> findAllFilesWithPageble(int page, int size){
        return ResponseEntity.ok(attachmentService.findAllWithPage(page, size));
    }
    @GetMapping("/file/prewiev/{hashids}")
    public  ResponseEntity<InputStreamResource> downloadToServer(@PathVariable String hashids, HttpServletResponse response) throws IOException {
       return attachmentService.downloadToServer(hashids, response);
    }

    @DeleteMapping("/file/delete/{hashids}")
    public ResponseEntity fileDelete(@PathVariable String hashids){
        return ResponseEntity.ok(attachmentService.delete(hashids));
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
    @PostMapping("/blog/save")
    public ResponseEntity saveBlog(@RequestBody BlogReq blog){
        return ResponseEntity.ok(blogService.saveBlog(blog));
    }
    @PutMapping("/blog/edit/{id}")
    public ResponseEntity saveBlog(@PathVariable Long id, @RequestBody BlogReq blog){
        return ResponseEntity.ok(blogService.edit(id, blog));
    }
    @DeleteMapping("/blog/delete/{id}")
    public ResponseEntity deleteBlog(@PathVariable Long id){
        return ResponseEntity.ok(blogService.deleteBlogById(id));
    }
    @GetMapping("/blogs")
    public ResponseEntity<Page<Blog>> findAllPage(@RequestParam int page, @RequestParam int size){
        return ResponseEntity.ok(blogService.getAll(page, size));
    }
    //endregion

    //region TeacherController
    @ApiOperation(value = "Teacher controller")
    @PostMapping("/teacher/save")
    public ResponseEntity<Result> addTeacher(@RequestBody UserReq userReq){
        Result result = teacherService.save(userReq);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    @PutMapping("/teacher/edit/{id}")
    public ResponseEntity<Result> editTeacher(@PathVariable Long id,@RequestBody UserReq userReq){
        Result result = teacherService.edit(id, userReq);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
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
    @DeleteMapping("/teacher/delete/{id}")
    public ResponseEntity<Result> deleteTeacherId(@PathVariable Long id){
        Result result = teacherService.delete(id);
        return  new ResponseEntity<>(result,HttpStatus.OK);
    }
    //endregion

    //region CategoryController
    @PostMapping("/category/save")
    public ResponseEntity saveCategory(@RequestBody CategoryRequest categoryRequest){
        return ResponseEntity.ok(categoryService.saveCategory(categoryRequest));
    }
    @PutMapping("/category/edit/{id}")
    public ResponseEntity editCategory(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest){
        return ResponseEntity.ok(categoryService.editCategory(id, categoryRequest));
    }
    @DeleteMapping("/category/delete/{id}")
    public ResponseEntity deleteCategory(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> findAllCategory(){
        return ResponseEntity.ok(categoryService.findAll());
    }
    //endregion

    //region CourseController
//    @ApiOperation(value = "Course controller")
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
    //endregion


}
