package nowak.wojtek.library.controller;

import nowak.wojtek.library.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/rating")
public class RatingController {

    @Autowired
    RatingService ratingService;

    @GetMapping
    public ResponseEntity getAuthorsRating() {
        return new ResponseEntity(ratingService.prepareAuthorsRating(), HttpStatus.OK);
    }
}
