package uz.zako.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.zako.example.entity.Role;
import uz.zako.example.entity.Teacher;
import uz.zako.example.model.Result;
import uz.zako.example.payload.UserReq;
import uz.zako.example.repository.RoleRepository;
import uz.zako.example.repository.TeacherRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherServiceImp implements TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AttachmentService attachmentService;
//    public TeacherServiceImp(TeacherRepository teacherRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository){
//        this.teacherRepository=teacherRepository;
//        this.passwordEncoder=passwordEncoder;
//        this.roleRepository=roleRepository;
//    }sfjd
    @Override
    public Result save(UserReq userReq) {
        try {
            Teacher teacher=new Teacher();
            teacher.setFirstName(userReq.getFirstName());
            teacher.setAttachment(attachmentService.findByHashId(userReq.getImgUrl()));
            teacher.setPhoneNumber(userReq.getPhoneNumber());
            teacher.setUserType("TEACHER");
            teacher.setLastName(userReq.getLastName());
            teacher.setPassword(userReq.getPassword());
//            teacher.setPassword(passwordEncoder.encode(userReq.getPassword()));
            teacher.setUsername(userReq.getUsername());
            teacher.setAboutUz(userReq.getAboutUz());
            teacher.setAboutRu(userReq.getAboutRu());
            teacher.setTelegram(userReq.getTelegram());
            teacher.setInstagram(userReq.getInstagram());
            teacher.setFacebook(userReq.getFacebook());
            teacher.setEmail(userReq.getEmail());
            List<Role> roles=new ArrayList<>();
            roles.add(roleRepository.findByName("ROLE_TEACHER"));
            teacher.setRoles(roles);
            teacherRepository.save(teacher);
            return new Result(true, "Saved");
        }catch (Exception e){
            return new Result(false,"Not saved");
        }
    }

    @Override
    public Result edit(Long id, UserReq userReq) {
        try {
            Teacher teacher=teacherRepository.findById(id).get();
            teacher.setAttachment(attachmentService.findByHashId(userReq.getImgUrl()));
            teacher.setPhoneNumber(userReq.getPhoneNumber());
            teacher.setUserType("TEACHER");
            teacher.setFirstName(userReq.getFirstName());
            teacher.setLastName(userReq.getLastName());
//            teacher.setPassword(passwordEncoder.encode(userReq.getPassword()));
            teacher.setPassword(userReq.getPassword());
            teacher.setUsername(userReq.getUsername());
            teacher.setAboutUz(userReq.getAboutUz());
            teacher.setAboutRu(userReq.getAboutRu());
            teacher.setTelegram(userReq.getTelegram());
            teacher.setInstagram(userReq.getInstagram());
            teacher.setFacebook(userReq.getFacebook());
            teacher.setEmail(userReq.getEmail());
            List<Role> roles=new ArrayList<>();
            roles.add(roleRepository.findByName("ROLE_TEACHER"));
            teacher.setRoles(roles);
            teacherRepository.save(teacher);
            return new Result(true,"Edited");
        }catch (Exception e){
            System.out.println(e);
            return new Result(false,"Not edited");
        }
    }

    @Override
    public Page<Teacher> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createAt").descending());
        return  teacherRepository.findAll(pageable);
    }

    @Override
    public Teacher findById(Long id) {
        try {
            return teacherRepository.findById(id).get();
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    @Override
    public Result delete(Long id) {
        try {
            teacherRepository.deleteById(id);
            return new Result(true,"Successful deleting");
        }catch (Exception e){
            System.out.println(e);
            return new Result(false,"Not deleted");
        }
    }

    @Override
    public List<Teacher> findAllTeachers() {
        try {
            return teacherRepository.findAll();
        }catch (Exception e){
            System.out.println(e);
        }
        return new ArrayList<>();
    }
}
