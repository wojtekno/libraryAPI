package nowak.wojtek.library.service;

import com.google.gson.*;
import nowak.wojtek.library.entity.AuthorDAO;
import nowak.wojtek.library.entity.BookDAO;
import nowak.wojtek.library.entity.CategoryDAO;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class BookJsonDeserializer implements JsonDeserializer<BookDAO> {


    @Override
    public BookDAO deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String isbn = null;
        String bookId = null;
        String title = null;
        String subtitle = null;
        String publisher = null;
        Long publishedDate = null;
        String description = null;
        Integer pageCount = null;
        String thumbnailUrl = null;
        String language = null;
        String previewLink = null;
        Double averageRating = null;
        Set<AuthorDAO> authors = new HashSet<>();
        Set<CategoryDAO> categories = new HashSet<>();
        Set<String> circles = new HashSet<>();

        JsonObject jsonBook = jsonElement.getAsJsonObject();
        Optional<JsonElement> idJson = Optional.ofNullable(jsonBook.get("id"));
        bookId = idJson.isPresent() ? idJson.get().getAsString() : null;

        Optional<JsonObject> volumeInfoOpt = Optional.ofNullable(jsonBook.getAsJsonObject("volumeInfo"));
        if (volumeInfoOpt.isPresent()) {
            Optional<JsonArray> indIdArrayOpt = Optional.ofNullable(volumeInfoOpt.get().getAsJsonArray("industryIdentifiers"));
            if (indIdArrayOpt.isPresent()) {
                for (JsonElement element : indIdArrayOpt.get()) {
                    Optional<JsonElement> indIdTypeOpt = Optional.ofNullable(element.getAsJsonObject().get("type"));
                    if (indIdTypeOpt.isPresent() && indIdTypeOpt.get().getAsString().equals("ISBN_13")) {
                        Optional<JsonElement> indId = Optional.ofNullable(element.getAsJsonObject().get("identifier"));
                        isbn = indId.isPresent() ? indId.get().getAsString() : null;
                    }
                }
            }
            Optional<JsonElement> titleJson = Optional.ofNullable(volumeInfoOpt.get().get("title"));
            title = titleJson.isPresent() ? titleJson.get().getAsString() : null;
            Optional<JsonElement> subtitleJson = Optional.ofNullable(volumeInfoOpt.get().get("subtitle"));
            subtitle = subtitleJson.isPresent() ? subtitleJson.get().getAsString() : null;
            Optional<JsonElement> publisherJson = Optional.ofNullable(volumeInfoOpt.get().get("publisher"));
            publisher = publisherJson.isPresent() ? publisherJson.get().getAsString() : null;
            Optional<JsonElement> publishedDateJson = Optional.ofNullable(volumeInfoOpt.get().get("publishedDate"));
            String publishedDateString = publishedDateJson.isPresent() ? publishedDateJson.get().getAsString() : null;
            publishedDate = TimeService.dateStringToUnixTime(publishedDateString);
            Optional<JsonElement> descriptionJson = Optional.ofNullable(volumeInfoOpt.get().get("description"));
            description = descriptionJson.isPresent() ? descriptionJson.get().getAsString() : null;
            Optional<JsonElement> pageCountJson = Optional.ofNullable(volumeInfoOpt.get().get("pageCount"));
            pageCount = pageCountJson.isPresent() ? pageCountJson.get().getAsInt() : null;
            Optional<JsonObject> imageLinksArrOpt = Optional.ofNullable(volumeInfoOpt.get().getAsJsonObject("imageLinks"));
            if (imageLinksArrOpt.isPresent()) {
                Optional<JsonElement> thumbnailUrlJson = Optional.ofNullable(imageLinksArrOpt.get().get("thumbnail"));
                thumbnailUrl = thumbnailUrlJson.isPresent() ? thumbnailUrlJson.get().getAsString() : null;
            }
            Optional<JsonElement> languageJson = Optional.ofNullable(volumeInfoOpt.get().get("language"));
            language = languageJson.isPresent() ? languageJson.get().getAsString() : null;
            Optional<JsonElement> previewLinkJson = Optional.ofNullable(volumeInfoOpt.get().get("previewLink"));
            previewLink = previewLinkJson.isPresent() ? previewLinkJson.get().getAsString() : null;
            Optional<JsonElement> averageRatingJson = Optional.ofNullable(volumeInfoOpt.get().get("averageRating"));
            averageRating = averageRatingJson.isPresent() ? averageRatingJson.get().getAsDouble() : null;
            Optional<JsonArray> authorsArrJson = Optional.ofNullable(volumeInfoOpt.get().getAsJsonArray("authors"));
            if (authorsArrJson.isPresent()) {
                for (JsonElement element : authorsArrJson.get()) {
                    authors.add(AuthorDAO.builder().name(element.getAsString()).build());
                }
            }
            Optional<JsonArray> categoriesArrJson = Optional.ofNullable(volumeInfoOpt.get().getAsJsonArray("categories"));
            if (categoriesArrJson.isPresent()) {
                for (JsonElement element : categoriesArrJson.get()) {
//                    categories.add(createBookCategoryDao(element.getAsString()));
                    categories.add(CategoryDAO.builder().name(element.getAsString()).build());
                }
            }
        }

        return BookDAO.builder()
                .isbn(isbn)
                .bookId(bookId)
                .title(title)
                .subtitle(subtitle)
                .publisher(publisher)
                .publishedDate(publishedDate)
                .description(description)
                .pageCount(pageCount)
                .thumbnailUrl(thumbnailUrl)
                .language(language)
                .previewLink(previewLink)
                .averageRating(averageRating)
                .authors(authors)
                .categories(categories) // TODO Wojtek
                .build();
    }
}
