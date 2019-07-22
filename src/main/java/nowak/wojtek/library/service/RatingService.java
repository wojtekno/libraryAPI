package nowak.wojtek.library.service;

import nowak.wojtek.library.entity.AuthorDAO;
import nowak.wojtek.library.model.AuthorRatingDTO;
import nowak.wojtek.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RatingService {

    @Autowired
    BookRepository bookRepository;


    public List<AuthorRatingDTO> prepareAuthorsRating() {
        Set<AuthorDAO> authors =  bookRepository.findAllAuthors();
        List<AuthorRatingDTO> authorRating = new ArrayList<>();
        for (AuthorDAO author : authors){
            List<Double> ratings = bookRepository.findAuthorRatings(author.getName());
            if(ratings != null && !ratings.isEmpty()){
                authorRating.add(new AuthorRatingDTO(author.getName(), ratings));
            }
        }
        return authorRating.stream().sorted((x,y)-> -(int)((x.getAverageRating()-y.getAverageRating())*100)).collect(Collectors.toList());
    }
}
