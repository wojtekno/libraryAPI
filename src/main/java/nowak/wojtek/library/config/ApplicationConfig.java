package nowak.wojtek.library.config;


import lombok.Getter;
import lombok.Setter;
import nowak.wojtek.library.entity.AuthorDAO;
import nowak.wojtek.library.entity.BookDAO;
import nowak.wojtek.library.entity.CategoryDAO;
import nowak.wojtek.library.model.BookDTO;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Set;

@Getter
@Setter
@Component
@PropertySource("classpath:application.yml")
@ConfigurationProperties
public class ApplicationConfig {

    @Bean
    public ModelMapper modelMapper() {
        Converter<BookDAO, BookDTO> bookDAOBookDTOConverter = ctx -> {
            String isbn = ctx.getSource().getIsbn() != null ? ctx.getSource().getIsbn() : ctx.getSource().getBookId();
            ctx.getDestination().setIsbn(isbn);
            Set<CategoryDAO> categories = ctx.getSource().getCategories();
            if (categories.isEmpty()) {
                ctx.getDestination().setCategories(null);
            }
            Set<AuthorDAO> authors = ctx.getSource().getAuthors();
            if (authors.isEmpty()) {
                ctx.getDestination().setAuthors(null);
            }
            return ctx.getDestination();
        };

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<BookDAO, BookDTO>() {
                    @Override
                    protected void configure() {
                    }
                })
                .setPostConverter(bookDAOBookDTOConverter);

        return modelMapper;
    }

}


