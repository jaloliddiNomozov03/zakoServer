package uz.zako.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.zako.example.entity.Blog;
import uz.zako.example.model.Result;
import uz.zako.example.payload.BlogReq;
import uz.zako.example.repository.BlogRepository;

import java.util.List;

@Service
public class BlogServiceIml implements BlogService {
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private CategoryService categoryService;
    @Override
    public Blog saveBlog(BlogReq blogReq) {
        try {
            Blog blog = new Blog();
            blog.setBlogDate(blogReq.getBlogDate());
            blog.setBlogCategory(categoryService.findById(blogReq.getBlogCategoryId()));
            blog.setBlogTitleUz(blogReq.getBlogTitleUz());
            blog.setBlogTitleRu(blogReq.getBlogTitleRu());
            blog.setBlogValueUz(blogReq.getBlogValueUz());
            blog.setBlogValueRu(blogReq.getBlogValueRu());
            blog.setHashids(blogReq.getHashids());
            blog.setStatus(blogReq.getStatus());
            blog.setBlogType(blogReq.getBlogType());
            return blogRepository.save(blog);
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    @Override
    public Blog edit(Long id, BlogReq blogReq) {
        try {
            Blog blog = blogRepository.findById(id).get();
            blog.setBlogDate(blogReq.getBlogDate());
            blog.setBlogCategory(categoryService.findById(blogReq.getBlogCategoryId()));
            blog.setBlogTitleUz(blogReq.getBlogTitleUz());
            blog.setBlogTitleRu(blogReq.getBlogTitleRu());
            blog.setBlogValueUz(blogReq.getBlogValueUz());
            blog.setBlogValueRu(blogReq.getBlogValueRu());
            blog.setHashids(blogReq.getHashids());
            blog.setStatus(blogReq.getStatus());
            blog.setBlogType(blogReq.getBlogType());
            return blogRepository.save(blog);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Page<Blog> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createAt").descending());
        return blogRepository.findAll(pageable);
    }

    @Override
    public Blog getById(Long id) {
        try {
            return blogRepository.findById(id).get();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Result deleteBlogById(Long id) {
        try {
            blogRepository.deleteById(id);
            return new Result(true, "Success deleted!");
        }catch (Exception e){
            return new Result(false, "Failed. Not deleted!");
        }
    }

    @Override
    public List<Blog> getAllByBlogTypeAndStatus(String status, String blogType) {
        return blogRepository.findAllByStatusAndBlogType(status,blogType);
    }
}
