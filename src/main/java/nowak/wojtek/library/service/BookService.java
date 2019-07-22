package nowak.wojtek.library.service;

import nowak.wojtek.library.entity.AuthorDAO;
import nowak.wojtek.library.entity.BookDAO;
import nowak.wojtek.library.entity.CategoryDAO;
import nowak.wojtek.library.model.BookDTO;
import nowak.wojtek.library.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private CategoryService categoryService;

    public List<BookDTO> getAllBooksDTO() {
        List<BookDAO> persistedBooks = bookRepository.findAll();
        if (persistedBooks == null || persistedBooks.isEmpty()) {
            return new ArrayList<>();
        }
        List<BookDTO> booksDTO = new ArrayList<>();
        for (BookDAO bookDAO : persistedBooks) {
            booksDTO.add(mapToBookDTO(bookDAO));
        }
        return booksDTO;
    }

    public BookDTO findBookByIsbnOrId(String isbn) {
        if (isbn == null) {
            throw new NullPointerException();
        } else if (isbn.isEmpty()) {
            throw new IllegalArgumentException();
        }
        BookDAO bookDAO = Optional.ofNullable(bookRepository.findByIsbn(isbn)).orElseGet(() -> bookRepository.findByBookId(isbn));
        return mapToBookDTO(bookDAO);
    }

    public List<BookDTO> findBooksByCategory(String categoryName) {
        if (categoryName == null) {
            throw new NullPointerException();
        } else if (categoryName.isEmpty()) {
            throw new IllegalArgumentException();
        }

        List<BookDAO> bookDAOList = bookRepository.findByCategoriesNameIgnoreCase(categoryName);
        if (bookDAOList == null || bookDAOList.isEmpty()) {
            return new ArrayList<BookDTO>();
        }
        List<BookDTO> bookDTOList = new ArrayList<>();
        for (BookDAO bookDAO : bookDAOList) {
            bookDTOList.add(mapToBookDTO(bookDAO));
        }
        return bookDTOList;
    }

    public List<BookDTO> findBooksByPhrase(String phrase) {
        if (phrase == null) {
            throw new NullPointerException();
        } else if (phrase.isEmpty()) {
            throw new IllegalArgumentException();
        }
        List<BookDAO> bookDAOList = bookRepository.findBooksContainigPhrase(phrase);
        if (bookDAOList == null || bookDAOList.isEmpty()) {
            return new ArrayList<>();
        }
        List<BookDTO> bookDTOList = new ArrayList<>();
        for (BookDAO bookDAO : bookDAOList) {
            bookDTOList.add(modelMapper.map(bookDAO, BookDTO.class));
        }
        return bookDTOList;
    }

    public BookDAO persistBookDAO(BookDAO bookDAO) {
        bookDAO.setAuthors(prepareAuthorDAOs(bookDAO.getAuthors()));
        bookDAO.setCategories(prepareCategoryDAOs(bookDAO.getCategories()));
        return bookRepository.save(bookDAO);
    }

    private Set<AuthorDAO> prepareAuthorDAOs(Set<AuthorDAO> auhtors) {
        Set<AuthorDAO> authorList = new HashSet<>();
        for (AuthorDAO author : auhtors) {
            authorList.add(authorService.persistAuthorDAO(author));
        }
        return authorList;
    }

    private Set<CategoryDAO> prepareCategoryDAOs(Set<CategoryDAO> categories) {
        Set<CategoryDAO> categorySet = new HashSet<>();
        for (CategoryDAO category : categories) {
            categorySet.add(categoryService.persistCategoryDAO(category));
        }
        return categorySet;
    }

    private BookDTO mapToBookDTO(BookDAO bookDAO) {
        if (bookDAO == null) {
            return null;
        }
        BookDTO bookDTO = modelMapper.map(bookDAO, BookDTO.class);
        return bookDTO;
    }
}
