package nowak.wojtek.library.service;

import com.google.gson.*;
import nowak.wojtek.library.entity.BookDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;


@Service
public class JSONService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Gson gson;

    @Autowired
    private GsonBuilder gsonBuilder;

    @Autowired
    BookService bookService;

    private Reader reader;
    private File jsonRepo;

    @Value("${app.jsonFile:classpath:books.json}")
    private String MAIN_JSON_FILE;

    @PostConstruct
    public void populateDatabase() {
        logger.info("MAIN_JSON_FILE: " + MAIN_JSON_FILE );
        JsonObject jsonObject;
        JsonArray items;
        List<BookDAO> bookList = new ArrayList<>();

        try {
            jsonRepo = ResourceUtils.getFile(MAIN_JSON_FILE);
            reader = new FileReader(jsonRepo);
            jsonObject = gson.fromJson(reader, JsonObject.class);
            items = jsonObject.getAsJsonArray("items");
            gsonBuilder.registerTypeAdapter(BookDAO.class, new BookJsonDeserializer());
            for (JsonElement item : items) {
                BookDAO bookDAO = gsonBuilder.create().fromJson(item, BookDAO.class);
                bookService.persistBookDAO(bookDAO);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
