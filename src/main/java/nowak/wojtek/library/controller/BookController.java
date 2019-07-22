package nowak.wojtek.library.controller;

import nowak.wojtek.library.entity.BookDAO;
import nowak.wojtek.library.model.BookDTO;
import nowak.wojtek.library.repository.BookRepository;
import nowak.wojtek.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("allbooks")
    public ResponseEntity getAllBooks() {
        List<BookDTO> bookList = bookService.getAllBooksDTO();
        if(bookList == null || bookList.isEmpty()){
            return new ResponseEntity("No books found in database", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(bookList, HttpStatus.OK);
        }
    }

    @GetMapping("{isbn}")
    public ResponseEntity getBookById(@PathVariable(name = "isbn") String isbn) {
        Optional<BookDTO> bookOpt = Optional.ofNullable(bookService.findBookByIsbnOrId(isbn));

        if (bookOpt.isPresent()) {
            return new ResponseEntity(bookOpt.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity("No results found", HttpStatus.NOT_FOUND);
        }
    }


}
