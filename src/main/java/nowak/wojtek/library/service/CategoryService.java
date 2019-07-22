package nowak.wojtek.library.service;

import nowak.wojtek.library.entity.CategoryDAO;
import nowak.wojtek.library.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.util.Set;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public CategoryDAO persistCategoryDAO(CategoryDAO categoryDAO) {
        if(categoryDAO == null){
            throw new NullPointerException();
        } else if(categoryDAO.getName().isEmpty()) {
            throw new IllegalArgumentException();
        }
        CategoryDAO persistedCat = categoryRepository.findByName(categoryDAO.getName());
        if (persistedCat == null) {
            return categoryRepository.save(categoryDAO);
        } else {
            return persistedCat;
        }
    }

}
