package nowak.wojtek.library.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorRatingDTO {

    private String author;
    private double averageRating;

    public AuthorRatingDTO(String authorName, List<Double> authorRatings) {
        this.author = authorName;
        this.averageRating = prepareAvRat(authorRatings);
//        int ratingCount = 0;
//
//        for (Double rating : authorRatings) {
//            if (rating != null) {
//                this.averageRating += rating;
//                ratingCount++;
//            }
//        }
//        if (ratingCount > 0) {
//            this.averageRating /= ratingCount;
//        }
    }

    private double prepareAvRat(List<Double> ratings) {
        int ratingCount = 0;
        double totalScore = 0;

        for (Double rating : ratings) {
            if (rating != null) {
                totalScore += rating;
                ratingCount++;
            }
        }

        if (ratingCount > 0) {
            return totalScore / ratingCount;
        } else {
            return 0;
        }
    }

}
