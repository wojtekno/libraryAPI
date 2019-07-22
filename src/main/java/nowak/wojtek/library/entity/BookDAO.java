package nowak.wojtek.library.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "BookDAO")
@Table(name = "book_dao")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDAO {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String isbn;
    private String bookId;
    private String title;
    private String subtitle;
    private String publisher;
    private Long publishedDate;
    private String description;
    private Integer pageCount;
    private String thumbnailUrl;
    @Column(name="_language")
    private String language;
    private String previewLink;
    private Double averageRating;

    @ManyToMany(fetch = FetchType.EAGER)
    Set<AuthorDAO> authors;

    @ManyToMany(fetch = FetchType.EAGER)
    Set<CategoryDAO> categories;



}
