package uz.zako.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.zako.example.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
