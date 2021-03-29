package uz.zako.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.zako.example.entity.Student;


@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
}
