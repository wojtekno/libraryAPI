package nowak.wojtek.library.repository;

import nowak.wojtek.library.entity.AuthorDAO;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<AuthorDAO, Long> {

    AuthorDAO findByName(String authorName);
}
