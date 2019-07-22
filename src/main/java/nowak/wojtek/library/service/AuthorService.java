package nowak.wojtek.library.service;

import nowak.wojtek.library.entity.AuthorDAO;
import nowak.wojtek.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public AuthorDAO persistAuthorDAO(AuthorDAO authorDAO) {
        if(authorDAO == null){
            throw new NullPointerException();
        } else if(authorDAO.getName().isEmpty()) {
            throw new IllegalArgumentException();
        }
        AuthorDAO persistedAuthor = authorRepository.findByName(authorDAO.getName());
        if(persistedAuthor == null){
           return authorRepository.save(authorDAO);
        } else {
            return persistedAuthor;
        }
    }
}
