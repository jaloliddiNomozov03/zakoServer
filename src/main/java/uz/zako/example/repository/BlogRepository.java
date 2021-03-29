package uz.zako.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.zako.example.entity.Blog;

import java.security.acl.LastOwnerException;
import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findAllByStatusAndBlogType(String status, String type);
}
