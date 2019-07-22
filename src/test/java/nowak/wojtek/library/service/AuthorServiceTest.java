package nowak.wojtek.library.service;

import nowak.wojtek.library.entity.AuthorDAO;
import nowak.wojtek.library.repository.AuthorRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(SpringJUnit4ClassRunner.class)
public class AuthorServiceTest {

    @InjectMocks
    private AuthorService service;

    @Mock
    AuthorRepository repository;

    private final String AUTHOR_1_NAME = "Author 1 Name";
    private AuthorDAO authorDAO1;
    private final String AUTHOR_2_NAME = "Author 1 Name";
    private AuthorDAO authorDAO2;

    @Before
    public void setUp() {
        authorDAO1 = AuthorDAO.builder().name(AUTHOR_1_NAME).build();
        authorDAO2 = AuthorDAO.builder().name(AUTHOR_2_NAME).build();

    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(repository);
    }

    @Test(expected = NullPointerException.class)
    public void persistAuthorDAOArgNULL() {
        service.persistAuthorDAO(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistAuthorDAOArgGetNameIsEmpty() {
        service.persistAuthorDAO(AuthorDAO.builder().name("").build());
    }


    @Test
    public void persistAuthorDAONotPersistedBefore() {
        when(repository.findByName(authorDAO1.getName())).thenReturn(null);
        when(repository.save(authorDAO1)).thenReturn(authorDAO1);

        assertEquals(authorDAO1, service.persistAuthorDAO(authorDAO1));

        verify(repository, times(1)).findByName(authorDAO1.getName());
        verify(repository, times(1)).save(authorDAO1);
    }

    @Test
    public void persistAuthorDAOPersistedBefore() {
        when(repository.findByName(authorDAO1.getName())).thenReturn(authorDAO1);

        assertEquals(authorDAO1, service.persistAuthorDAO(authorDAO1));

        verify(repository, times(1)).findByName(authorDAO1.getName());
    }

    @Test
    public void persistAuthorDAOTryTwiceSameAuthorName() {
        when(repository.findByName(authorDAO1.getName())).thenReturn(null);
        when(repository.save(authorDAO1)).thenReturn(authorDAO1);

        assertEquals(authorDAO1, service.persistAuthorDAO(authorDAO1));

        when(repository.findByName(authorDAO2.getName())).thenReturn(authorDAO1);
        assertEquals(authorDAO1, service.persistAuthorDAO(authorDAO2));

        verify(repository, times(2)).findByName(AUTHOR_1_NAME);
        verify(repository, times(1)).save(authorDAO1);
    }

}