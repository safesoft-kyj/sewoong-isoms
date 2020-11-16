package com.cauh.esop.service;

import com.cauh.esop.domain.Category;
import com.cauh.esop.domain.QCategory;
import com.cauh.esop.repository.CategoryRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Resource
    private CategoryRepository categoryRepository;

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category findById(String id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        return optionalCategory.isPresent() ? optionalCategory.get() : null;
    }

    public Optional<Category> findByShortName(String shortName) {
        QCategory qCategory = QCategory.category;
        return categoryRepository.findOne(qCategory.shortName.eq(shortName));
    }

    public List<Category> getCategoryList() {
        return categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "shortName"));
    }

    public TreeMap<String, String> categoryMap() {
        TreeMap<String, String> categoryMap = new TreeMap<>();
        categoryMap.putAll(getCategoryList().stream().collect(Collectors.toMap(c -> c.getShortName(), c-> "[" + c.getShortName() + "] " + c.getName())));
        return categoryMap;
    }

}
