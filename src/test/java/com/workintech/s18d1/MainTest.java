package com.workintech.s18d1;

import com.workintech.s18d1.dao.BurgerDao;
import com.workintech.s18d1.dao.BurgerDaoImpl;
import com.workintech.s18d1.entity.BreadType;
import com.workintech.s18d1.entity.Burger;
import com.workintech.s18d1.exceptions.BurgerErrorResponse;
import com.workintech.s18d1.exceptions.BurgerException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(ResultAnalyzer2.class)
@SpringBootTest
class MainTest {


    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private BurgerDaoImpl burgerDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEnumConstants() {

        assertTrue(BreadType.valueOf("BURGER") == BreadType.BURGER);
        assertTrue(BreadType.valueOf("WRAP") == BreadType.WRAP);
        assertTrue(BreadType.valueOf("DOUBLE") == BreadType.DOUBLE);
    }

    @Test
    void testEnumValues() {

        assertEquals(3, BreadType.values().length);
    }

    @Test
    void testBurgerSetAndGet() {
        Burger burger = new Burger();
        burger.setId(1L);
        burger.setName("Vegan Delight");
        burger.setPrice(8.99);
        burger.setIsVegan(true);
        burger.setBreadType(BreadType.WRAP);
        burger.setContents("Lettuce, Tomato, Vegan Patty, Avocado");


        assertEquals(1L, burger.getId());
        assertEquals("Vegan Delight", burger.getName());
        assertEquals(8.99, burger.getPrice());
        assertEquals(true, burger.getIsVegan());
        assertEquals(BreadType.WRAP, burger.getBreadType());
        assertEquals("Lettuce, Tomato, Vegan Patty, Avocado", burger.getContents());
    }

    @Test
    void testNoArgsConstructor() {

        Burger burger = new Burger();
        assertNull(burger.getName());

    }


    @Test
    void testSave() {
        Burger burger = new Burger();
        burger.setId(1L);
        burgerDao.save(burger);
        verify(entityManager).persist(burger);
    }

  @Test
  void testFindAll() {
    // 1. Create a mock for the TypedQuery object
    TypedQuery<Burger> query = mock(TypedQuery.class);

    // 2. Mock the behavior of entityManager.createQuery().
    //    When it's called, return the mocked query object.
    when(entityManager.createQuery(anyString(), eq(Burger.class))).thenReturn(query);

    // 3. Mock the behavior of the mocked query object.
    //    When getResultList() is called, return a predefined list of burgers.
    when(query.getResultList()).thenReturn(Arrays.asList(new Burger(), new Burger()));

    // 4. Call the method under test
    List<Burger> burgers = burgerDao.findAll();

    // 5. Assert that the returned list has the expected size
    assertEquals(2, burgers.size());
  }

    @Test
    void testFindById_Exists() {
        Burger burger = new Burger();
        burger.setId(1L);
        when(entityManager.find(Burger.class, 1L)).thenReturn(burger);
        Burger found = burgerDao.findById(1L);
        assertEquals(1L, found.getId());
    }

    @Test
    void testFindById_NotExists() {
        when(entityManager.find(Burger.class, 999L)).thenReturn(null);
        assertThrows(BurgerException.class, () -> burgerDao.findById(999L));
    }

  @Test
  void testUpdate() {
    Burger burger = new Burger();
    burger.setId(1L);
    // Corrected: The merge operation should return the updated burger object.
    when(entityManager.merge(burger)).thenReturn(burger);
    Burger updated = burgerDao.update(burger);
    assertEquals(1L, updated.getId());
  }

  @Test
  void testRemove() {
    Burger burger = new Burger();
    burger.setId(1L);
    // Mock findById to return the burger
    when(burgerDao.findById(1L)).thenReturn(burger);
    // Mock the entityManager remove method
    doNothing().when(entityManager).remove(burger);

    Burger removed = burgerDao.remove(1L);
    assertEquals(1L, removed.getId());
  }

  @Test
  void testFindByPrice() {
    TypedQuery<Burger> query = mock(TypedQuery.class);
    when(entityManager.createQuery(anyString(), eq(Burger.class))).thenReturn(query);
    // Mock the setParameter call to return the query object
    when(query.setParameter(eq("price"), anyDouble())).thenReturn(query);
    when(query.getResultList()).thenReturn(Arrays.asList(new Burger(), new Burger()));

    List<Burger> burgers = burgerDao.findByPrice(10);
    assertEquals(2, burgers.size());
  }


  @Mock
  private TypedQuery<Burger> query;

  @Test
  void testFindByBreadType() {
    when(entityManager.createQuery(anyString(), eq(Burger.class))).thenReturn(query);
    when(query.setParameter(eq("breadType"), any())).thenReturn(query);
    when(query.getResultList()).thenReturn(Arrays.asList(new Burger(), new Burger()));

    List<Burger> burgers = burgerDao.findByBreadType(BreadType.BURGER);
    assertEquals(2, burgers.size());
  }


  @Test
  void testFindByContent() {
    TypedQuery<Burger> query = mock(TypedQuery.class);
    when(entityManager.createQuery(anyString(), eq(Burger.class))).thenReturn(query);
    // Mock the setParameter call to return the query object
    when(query.setParameter(eq("content"), anyString())).thenReturn(query);
    when(query.getResultList()).thenReturn(Arrays.asList(new Burger(), new Burger()));

    List<Burger> burgers = burgerDao.findByContent("cheese");
    assertEquals(2, burgers.size());
  }
  @Test
  void testImplementsBurgerDaoInterface() {
    EntityManager mockEntityManager = mock(EntityManager.class);
    BurgerDaoImpl burgerDaoImpl = new BurgerDaoImpl(mockEntityManager);
    assertTrue(burgerDaoImpl instanceof BurgerDao, "BurgerDaoImpl should implement BurgerDao interface");
  }


  @Test
    void testBurgerErrorResponse() {
        String expectedMessage = "An error occurred";
        BurgerErrorResponse errorResponse = new BurgerErrorResponse(expectedMessage);

        assertEquals(expectedMessage, errorResponse.getMessage(), "The retrieved message should match the expected message.");
    }

    @Test
    void testBurgerExceptionCreation() {
        String expectedMessage = "Test exception message";
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;

        BurgerException exception = new BurgerException(expectedMessage, expectedStatus);


        assertEquals(expectedMessage, exception.getMessage(), "The exception message should match the expected value.");
        assertEquals(expectedStatus, exception.getHttpStatus(), "The HttpStatus should match the expected value.");
    }

    @Test
    void testBurgerExceptionIsRuntimeException() {
        BurgerException exception = new BurgerException("Test", HttpStatus.BAD_REQUEST);


        assertTrue(exception instanceof RuntimeException, "BurgerException should be an instance of RuntimeException.");
    }
}
