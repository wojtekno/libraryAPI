package nowak.wojtek.library.repository;

import nowak.wojtek.library.entity.AuthorDAO;
import nowak.wojtek.library.entity.BookDAO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BookRepository extends CrudRepository<BookDAO, Long> {

    BookDAO findByIsbn(String isbn);

    BookDAO findByBookId(String isbn);

    List<BookDAO> findByCategoriesNameIgnoreCase(String categoryName);

    List<BookDAO> findByIsbnContainingOrBookIdContainingOrTitleContainingOrSubtitleContainingOrPublisherContainingOrDescriptionContainingOrThumbnailUrlContainingOrLanguageContainingOrPreviewLinkContainingIgnoreCase(String p1, String p2, String p3, String p4, String p5, String p6, String p7, String p8, String p9);

    @Query(value = "SELECT distinct b FROM BookDAO b LEFT JOIN b.categories c LEFT JOIN b.authors a" +
            " WHERE UPPER(b.isbn) LIKE UPPER(CONCAT('%',:phrase,'%'))" +
            " OR UPPER(b.bookId) LIKE UPPER(CONCAT('%',:phrase,'%'))" +
            " OR UPPER(b.title) LIKE UPPER(CONCAT('%',:phrase,'%'))" +
            " OR UPPER(b.subtitle) LIKE UPPER(CONCAT('%',:phrase,'%'))" +
            " OR UPPER(b.publisher) LIKE UPPER(CONCAT('%',:phrase,'%'))" +
            " OR UPPER(b.publishedDate) LIKE UPPER(CONCAT('%',:phrase,'%'))" +
            " OR UPPER(b.description) LIKE UPPER(CONCAT('%',:phrase,'%'))" +
            " OR UPPER(b.pageCount) LIKE UPPER(CONCAT('%',:phrase,'%'))" +
            " OR UPPER(b.thumbnailUrl) LIKE UPPER(CONCAT('%',:phrase,'%'))" +
            " OR UPPER(b.language) LIKE UPPER(CONCAT('%',:phrase,'%'))" +
            " OR UPPER(b.previewLink) LIKE UPPER(CONCAT('%',:phrase,'%'))" +
            " OR UPPER(b.averageRating) LIKE UPPER(CONCAT('%',:phrase,'%'))" +
            " OR UPPER(c.name) LIKE UPPER(CONCAT('%',:phrase,'%'))" +
            " OR UPPER(a.name) LIKE UPPER(CONCAT('%',:phrase,'%'))")
    List<BookDAO> findBooksContainigPhrase(@Param(value = "phrase") String phrase);

    @Query(value = "SELECT b.averageRating from BookDAO b left join b.authors a where a.name = :author and b.averageRating IS NOT NULL")
    List<Double> findAuthorRatings(@Param(value = "author") String author);

    @Query(value = "SELECT distinct a from BookDAO b join b.authors a")
    Set<AuthorDAO> findAllAuthors();

    List<BookDAO> findAll();

}
