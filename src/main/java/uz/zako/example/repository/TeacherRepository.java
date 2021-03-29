package uz.zako.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.zako.example.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
