package uz.zako.example.service;

import org.springframework.data.domain.Page;
import uz.zako.example.entity.Category;
import uz.zako.example.model.Result;
import uz.zako.example.payload.CategoryRequest;

import java.util.List;

public interface CategoryService {
    Category saveCategory(CategoryRequest categoryRequest);
    Category editCategory(Long id, CategoryRequest categoryRequest);
    Category findById(Long id);
    List<Category> findAll();
    Page<Category> findAllWithPage(int page, int size);
    Result deleteCategory(Long id);

}
