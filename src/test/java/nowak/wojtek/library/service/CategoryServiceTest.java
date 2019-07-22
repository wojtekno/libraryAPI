package nowak.wojtek.library.service;

import nowak.wojtek.library.entity.CategoryDAO;
import nowak.wojtek.library.repository.CategoryRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class CategoryServiceTest {

    @InjectMocks
    private CategoryService service;

    @Mock
    CategoryRepository categoryRepository;

    private final String CAT_1_NAME = "Category1Name";
    private CategoryDAO categoryDAO1;
    private final String CAT_2_NAME = "Category1Name";
    private CategoryDAO categoryDAO2;

    @Before
    public void setUp() {
        categoryDAO1 = CategoryDAO.builder().name(CAT_1_NAME).build();
        categoryDAO2 = CategoryDAO.builder().name(CAT_2_NAME).build();

    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test(expected = NullPointerException.class)
    public void persistCategoryDAOArgNULL() {
        service.persistCategoryDAO(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistCategoryDAOArgGetNameIsEmpty() {
        service.persistCategoryDAO(CategoryDAO.builder().name("").build());
    }


    @Test
    public void persistCategoryDAONotPersistedBefore() {
        when(categoryRepository.findByName(categoryDAO1.getName())).thenReturn(null);
        when(categoryRepository.save(categoryDAO1)).thenReturn(categoryDAO1);

        assertEquals(categoryDAO1, service.persistCategoryDAO(categoryDAO1));
        verify(categoryRepository, times(1)).findByName(categoryDAO1.getName());
        verify(categoryRepository, times(1)).save(categoryDAO1);
    }

    @Test
    public void persistCategoryDAOPersistedBefore() {
        when(categoryRepository.findByName(categoryDAO1.getName())).thenReturn(categoryDAO1);

        assertEquals(categoryDAO1, service.persistCategoryDAO(categoryDAO1));

        verify(categoryRepository, times(1)).findByName(categoryDAO1.getName());
    }

    @Test
    public void persistCategoryDAOTryTwiceSameCategoryName() {
        when(categoryRepository.findByName(categoryDAO1.getName())).thenReturn(null);
        when(categoryRepository.save(categoryDAO1)).thenReturn(categoryDAO1);

        assertEquals(categoryDAO1, service.persistCategoryDAO(categoryDAO1));

        when(categoryRepository.findByName(categoryDAO2.getName())).thenReturn(categoryDAO1);
        assertEquals(categoryDAO1, service.persistCategoryDAO(categoryDAO2));

        verify(categoryRepository, times(2)).findByName(CAT_1_NAME);
        verify(categoryRepository, times(1)).save(categoryDAO1);
    }

}