package uz.zako.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.zako.example.entity.Course;
import uz.zako.example.entity.Role;
import uz.zako.example.entity.Student;
import uz.zako.example.model.Result;
import uz.zako.example.payload.ObjectReq;
import uz.zako.example.payload.UserReq;
import uz.zako.example.repository.CourseRepository;
import uz.zako.example.repository.RoleRepository;
import uz.zako.example.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CourseRepository courseRepository;
    @Override
    public Student save(UserReq userReq) {
        try {
            Student student=new Student();
            student.setUserType("STUDENT");
            student.setFirstName(userReq.getFirstName());
//            student.setPassword(passwordEncoder.encode(userReq.getPassword()));
            student.setPassword(userReq.getPassword());
            student.setUsername(userReq.getUsername());
            student.setLastName(userReq.getLastName());
            student.setPhoneNumber(userReq.getPhoneNumber());
            student.setEmail(userReq.getEmail());
            List<ObjectReq> list = userReq.getGroups();
            List<Course> courses = new ArrayList<>();
            for (ObjectReq req : list) {
                Course course = courseRepository.getOne(Long.valueOf(req.getValue()));
                courses.add(course);
            }
            student.setCourses(courses);
            List<Role> roles=new ArrayList<>();
            roles.add(roleRepository.findByName("ROLE_STUDENT"));
            student.setRoles(roles);
            return studentRepository.save(student);
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    @Override
    public Student edit(Long id, UserReq userReq) {
        try {
            Student student=studentRepository.findById(id).get();
            student.setUserType("STUDENT");
            student.setFirstName(userReq.getFirstName());
            student.setLastName(userReq.getLastName());
//            student.setPassword(passwordEncoder.encode(userReq.getPassword()));
            student.setPassword(userReq.getPassword());
            student.setUsername(userReq.getUsername());
            student.setPhoneNumber(userReq.getPhoneNumber());
            student.setEmail(userReq.getEmail());
            List<ObjectReq> list = userReq.getGroups();
            List<Course> courses = new ArrayList<>();
            for (ObjectReq req : list) {
                Course course = courseRepository.getOne(Long.valueOf(req.getValue()));
                courses.add(course);
            }
            student.setCourses(courses);
            List<Role> roles=new ArrayList<>();
            roles.add(roleRepository.findByName("ROLE_STUDENT"));
            student.setRoles(roles);
            return studentRepository.save(student);
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    @Override
    public Page<Student> findAll(int page, int size) {
        Pageable pageable= PageRequest.of(page, size, Sort.by("createAt").descending());
        return studentRepository.findAll(pageable);
    }

    @Override
    public Student findById(Long id) {
       try {
           return studentRepository.findById(id).get();
       }catch (Exception e){
           System.out.println(e);
           return null;
       }
    }

    @Override
    public Result delete(Long id) {
        try {
            studentRepository.deleteById(id);
            return new Result(true,"Successfull deleting");
        }catch (Exception e){
            System.out.println(e);
            return new Result(false,"No deleting");
        }
    }

}
