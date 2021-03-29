package uz.zako.example.service;

import org.springframework.data.domain.Page;
import uz.zako.example.entity.Teacher;
import uz.zako.example.model.Result;
import uz.zako.example.payload.UserReq;

import java.util.List;

public interface TeacherService {
    Result save(UserReq userReq);
    Result edit(Long id, UserReq userReq);
    Page<Teacher> findAll(int page, int size);
    Teacher findById(Long id);
    Result delete(Long id);
    List<Teacher> findAllTeachers();
}
