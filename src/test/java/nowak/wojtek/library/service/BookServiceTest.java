package nowak.wojtek.library.service;

import nowak.wojtek.library.entity.BookDAO;
import nowak.wojtek.library.model.BookDTO;
import nowak.wojtek.library.repository.BookRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@RunWith(SpringJUnit4ClassRunner.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    BookRepository bookRepository;

    @Mock
    ModelMapper modelMapper;

    public static final String ISBN = "isbn";
    public static final String BOOK_ID = "bookId";
    private static final String CATEGORY_NAME = "categoryName";
    private static final String PHRASE_1 = "phrase1";

    private BookDAO bookDAOIsbn;
    private BookDAO bookDAOId;
    private BookDTO bookDTOIsbn;
    private BookDTO bookDTOId;
    private List<BookDAO> bookDAOList;

    @Before
    public void setUp() {
        bookDAOIsbn = BookDAO.builder().isbn(ISBN).build();
        bookDAOId = BookDAO.builder().bookId(BOOK_ID).build();
        bookDTOIsbn = BookDTO.builder().isbn(ISBN).build();
        bookDTOId = BookDTO.builder().isbn(BOOK_ID).build();
        bookDAOList = new ArrayList<>();
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(bookRepository, modelMapper);
    }

    /*
    Test bookService.findBookByIsbnOrId
    */

    @Test(expected = NullPointerException.class)
    public void findBookByIsbnOrIdParamNull() {
        bookService.findBookByIsbnOrId(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findBookByIsbnOrIdParamIsEmpty() {
        bookService.findBookByIsbnOrId("");
    }

    @Test
    public void findBookByIsbnOrIdNoMatch() {
        when(bookRepository.findByIsbn(ISBN)).thenReturn(null);
        when(bookRepository.findByBookId(ISBN)).thenReturn(null);
        assertNull(bookService.findBookByIsbnOrId(ISBN));
        verify(bookRepository, times(1)).findByIsbn(ISBN);
        verify(bookRepository, times(1)).findByBookId(ISBN);
    }

    @Test
    public void findBookByIsbnOrIdMatchIsbn() {
        when(bookRepository.findByIsbn(ISBN)).thenReturn(bookDAOIsbn);
        when(modelMapper.map(bookDAOIsbn, BookDTO.class)).thenReturn(bookDTOIsbn);
        assertEquals(bookDTOIsbn, bookService.findBookByIsbnOrId(ISBN));
        verify(bookRepository, times(1)).findByIsbn(ISBN);
        verify(bookRepository, times(0)).findByBookId(ISBN);
        verify(modelMapper, times(1)).map(bookDAOIsbn, BookDTO.class);

    }

    @Test
    public void findBookByIsbnOrIdNoIsbnMatchId() {
        when(bookRepository.findByIsbn(BOOK_ID)).thenReturn(null);
        when(bookRepository.findByBookId(BOOK_ID)).thenReturn(bookDAOId);
        when(modelMapper.map(bookDAOId, BookDTO.class)).thenReturn(bookDTOId);
        assertEquals(bookDTOId, bookService.findBookByIsbnOrId(BOOK_ID));
        verify(bookRepository, times(1)).findByIsbn(BOOK_ID);
        verify(bookRepository, times(1)).findByBookId(BOOK_ID);
        verify(modelMapper, times(1)).map(bookDAOId, BookDTO.class);
    }


    @Test
    public void findBookByIsbnOrIdMatchBothIsbnAndId() {
        when(bookRepository.findByIsbn(ISBN)).thenReturn(bookDAOIsbn);
        when(bookRepository.findByBookId(ISBN)).thenReturn(bookDAOId);
        when(modelMapper.map(bookDAOIsbn, BookDTO.class)).thenReturn(bookDTOIsbn);
        assertEquals(bookDTOIsbn, bookService.findBookByIsbnOrId(ISBN));
        verify(bookRepository, times(1)).findByIsbn(ISBN);
        verify(bookRepository, times(0)).findByBookId(ISBN);
        verify(modelMapper, times(1)).map(bookDAOIsbn, BookDTO.class);

    }

    /*
    test bookService.findBooksByCategory
    */

    @Test(expected = NullPointerException.class)
    public void findBooksByCategoryForNull() {
        bookService.findBooksByCategory(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findBooksByCategoryParamIsEmpty() {
        bookService.findBookByIsbnOrId("");
    }

    @Test
    public void findBooksByCategoryNoBooksFound() {
        when(bookRepository.findByCategoriesNameIgnoreCase(CATEGORY_NAME)).thenReturn(null);
        assertTrue(bookService.findBooksByCategory(CATEGORY_NAME).isEmpty());
        verify(bookRepository, times(1)).findByCategoriesNameIgnoreCase(CATEGORY_NAME);
    }

    @Test
    public void findBooksByCategoryFoundBooks() {
        bookDAOList.add(bookDAOIsbn);
        bookDAOList.add(bookDAOId);
        when(bookRepository.findByCategoriesNameIgnoreCase(CATEGORY_NAME)).thenReturn(bookDAOList);
        assertEquals(2, bookService.findBooksByCategory(CATEGORY_NAME).size());
        verify(bookRepository, times(1)).findByCategoriesNameIgnoreCase(CATEGORY_NAME);
        verify(modelMapper, times(1)).map(bookDAOIsbn, BookDTO.class);
        verify(modelMapper, times(1)).map(bookDAOId, BookDTO.class);
    }

    /*
    Test bookService.findBooksByPhrase
     */
    @Test(expected = NullPointerException.class)
    public void findBooksByPhraseForNull() {
        bookService.findBooksByPhrase(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findBooksByPhraseForEmptyPhrase() {
        bookService.findBooksByPhrase("");
    }

    @Test
    public void findBooksByPhraseNoBooksFound() {
        when(bookRepository.findBooksContainigPhrase(PHRASE_1)).thenReturn(null);
        assertTrue(bookService.findBooksByPhrase(PHRASE_1).isEmpty());
        verify(bookRepository, times(1)).findBooksContainigPhrase(PHRASE_1);

    }

    @Test
    public void findBooksByPhraseBooksFound() {
        bookDAOList.add(bookDAOIsbn);
        bookDAOList.add(bookDAOId);
        when(bookRepository.findBooksContainigPhrase(PHRASE_1)).thenReturn(bookDAOList);
        when(modelMapper.map(bookDAOIsbn, BookDTO.class)).thenReturn(bookDTOIsbn);
        when(modelMapper.map(bookDAOId, BookDTO.class)).thenReturn(bookDTOId);
        List<BookDTO> returnedList = bookService.findBooksByPhrase(PHRASE_1);
        assertEquals(2, returnedList.size());
        assertTrue(returnedList.contains(bookDTOId));
        assertTrue(returnedList.contains(bookDTOIsbn));
        verify(bookRepository, times(1)).findBooksContainigPhrase(PHRASE_1);
        verify(modelMapper, times(1)).map(bookDAOIsbn, BookDTO.class);
        verify(modelMapper, times(1)).map(bookDAOId, BookDTO.class);
    }


}