package uz.zako.example.service;

import org.springframework.data.domain.Page;
import uz.zako.example.entity.Student;
import uz.zako.example.model.Result;
import uz.zako.example.payload.UserReq;

import java.util.List;

public interface StudentService {
    public Student save(UserReq userReq);
    public Student edit(Long id,UserReq userReq);
    public Page<Student> findAll(int page, int size);
    public Student findById(Long id);
    public Result delete(Long id);
}
