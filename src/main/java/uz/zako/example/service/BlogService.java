package uz.zako.example.service;

import org.springframework.data.domain.Page;
import uz.zako.example.entity.Blog;
import uz.zako.example.model.Result;
import uz.zako.example.payload.BlogReq;

import java.util.List;

public interface BlogService {
    Blog saveBlog(BlogReq blogReq);
    Blog edit(Long id, BlogReq blogReq);
    Page<Blog> getAll(int page, int size);
    Blog getById(Long id);
    Result deleteBlogById(Long id);
    List<Blog> getAllByBlogTypeAndStatus(String status, String blogType);
}
