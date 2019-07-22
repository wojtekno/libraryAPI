package nowak.wojtek.library.controller;

import nowak.wojtek.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/search")
public class SearchController {

    @Autowired
    BookService bookService;

    @GetMapping
    public ResponseEntity searchBooksContainingPhrase(@RequestParam(value = "q") String phrase){
        return new ResponseEntity(bookService.findBooksByPhrase(phrase), HttpStatus.OK);
    }
}
