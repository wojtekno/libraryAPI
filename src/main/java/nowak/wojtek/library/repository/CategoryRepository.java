package nowak.wojtek.library.repository;

import nowak.wojtek.library.entity.CategoryDAO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryDAO, Long>{

    List<CategoryDAO> findAll();

    CategoryDAO findByName(String name);
}
