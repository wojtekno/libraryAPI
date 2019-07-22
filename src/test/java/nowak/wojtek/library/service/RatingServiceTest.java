package nowak.wojtek.library.service;

import edu.emory.mathcs.backport.java.util.Arrays;
import nowak.wojtek.library.entity.AuthorDAO;
import nowak.wojtek.library.model.AuthorRatingDTO;
import nowak.wojtek.library.repository.BookRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class RatingServiceTest {

    @InjectMocks
    private RatingService ratingService;

    @Mock
    BookRepository bookRepository;

    private AuthorDAO author1;
    private AuthorDAO author2;
    private Set<AuthorDAO> authorSet;
    private Double[] author1Rating;
    private Double[] author2Rating;


    @Before
    public void setUp() {
        author1 = AuthorDAO.builder().name("Author 1").build();
        author2 = AuthorDAO.builder().name("Author 2").build();
        authorSet = new HashSet<>();
    }

    @Test
    public void prepareAuthorsRatingNoAuthorsFound(){
        when(bookRepository.findAllAuthors()).thenReturn(new HashSet<>());
        assertTrue(ratingService.prepareAuthorsRating().isEmpty());
        verify(bookRepository, times(1)).findAllAuthors();
    }

    @Test
    public void prepareAuthorsRatingOneAuthorFound(){
        authorSet.add(author1);
        author1Rating = new Double[] {4.5};
        when(bookRepository.findAllAuthors()).thenReturn(authorSet);
        when(bookRepository.findAuthorRatings(author1.getName())).thenReturn(Arrays.asList(author1Rating));

        List<AuthorRatingDTO> authorRatings = ratingService.prepareAuthorsRating();

        assertEquals(1,authorRatings.size());
        assertEquals(author1.getName(), authorRatings.get(0).getAuthor());

        verify(bookRepository, times(1)).findAllAuthors();
        verify(bookRepository, times(1)).findAuthorRatings(author1.getName());
    }

    @Test
    public void prepareAuthorsRatingTwoAuthorsFound(){
        authorSet.add(author1);
        authorSet.add(author2);
        author1Rating = new Double[] {4.5, 4.8};
        author2Rating = new Double[] {4.5, 4.9};
        when(bookRepository.findAllAuthors()).thenReturn(authorSet);
        when(bookRepository.findAuthorRatings(author1.getName())).thenReturn(Arrays.asList(author1Rating));
        when(bookRepository.findAuthorRatings(author2.getName())).thenReturn(Arrays.asList(author2Rating));

        List<AuthorRatingDTO> authorRatings = ratingService.prepareAuthorsRating();

        assertEquals(2,authorRatings.size());
        assertEquals(author2.getName(), authorRatings.get(0).getAuthor());
        assertEquals(4.7 , authorRatings.get(0).getAverageRating(), 0);

        assertEquals(author1.getName(), authorRatings.get(1).getAuthor());
        assertEquals(4.65, authorRatings.get(1).getAverageRating(),0);

        verify(bookRepository, times(1)).findAllAuthors();
        verify(bookRepository, times(1)).findAuthorRatings(author1.getName());
        verify(bookRepository, times(1)).findAuthorRatings(author2.getName());
    }

}