package uz.zako.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.zako.example.entity.Category;
import uz.zako.example.model.Result;
import uz.zako.example.payload.CategoryRequest;
import uz.zako.example.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public Category saveCategory(CategoryRequest categoryRequest) {
        try {
            Category categorie= new Category();
            categorie.setCategoryNameUz(categoryRequest.getCategoryNameUz());
            categorie.setCategoryNameRu(categoryRequest.getCategoryNameRu());
            return categoryRepository.save(categorie);
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Category editCategory(Long id, CategoryRequest categoryRequest) {
        try {
            Category categorie= categoryRepository.findById(id).get();
            categorie.setCategoryNameUz(categoryRequest.getCategoryNameUz());
            categorie.setCategoryNameRu(categoryRequest.getCategoryNameRu());
            return categoryRepository.save(categorie);
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Category findById(Long id) {
        try {
            return categoryRepository.findById(id).get();
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public List<Category> findAll() {
        try {
            return categoryRepository.findAll();
        }catch (Exception e){
            System.out.println(e);
        }
        return new ArrayList<>();
    }

    @Override
    public Page<Category> findAllWithPage(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("createAt").descending());
            return categoryRepository.findAll(pageable);
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Result deleteCategory(Long id) {
        try {
            categoryRepository.deleteById(id);
            return new Result(true, "Successful deleted!");
        }catch (Exception e){
            System.out.println(e);
            return new Result(false,"Failed! Not deleted record!");
        }
    }
}
