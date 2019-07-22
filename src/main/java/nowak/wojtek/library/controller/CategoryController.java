package nowak.wojtek.library.controller;

import nowak.wojtek.library.model.BookDTO;
import nowak.wojtek.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/category")
public class CategoryController {

    @Autowired
    BookService bookService;

    @GetMapping("{categoryName}/books")
    public ResponseEntity getBooksFromCategory(@PathVariable(name = "categoryName") String categoryName) {
        List<BookDTO> bookList = new ArrayList<>();
        bookList = bookService.findBooksByCategory(categoryName);
        return new ResponseEntity(bookList, HttpStatus.OK);
    }

}
