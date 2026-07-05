package htw.webtech.NutriTrack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;


@SpringBootTest
public class MealServiceTest {

    @Autowired
    private MealService service;

    @MockitoBean
    private MealRepository repository;

    private MealEntry burger;
    private MealEntry pizza;
    private String owner;

    @BeforeEach
    public void setUpMealEntriesAndOwner(){
        burger = new MealEntry("Burger", new Macronutrient(20, 3, 2));
        pizza = new MealEntry("Pizza", new Macronutrient(39,4,2));
        owner = "test@test.de";
    }

    @Test
    public void testGetAllMeals(){
        List<MealEntry> expected = List.of(burger, pizza);

        doReturn(expected).when(repository).findByOwner(owner);

        List<MealEntry> actual = service.getAllMeals(owner);

        assertEquals(2, actual.size());
        assertEquals("Burger", actual.getFirst().getName());
    }

    @Test
    public void testUpdateChangesName(){
        doReturn(Optional.of(burger)).when(repository).findById(1L);
        doReturn(burger).when(repository).save(burger);

        MealEntry actual = service.update(1L, pizza, owner);

        assertEquals("Pizza", actual.getName());
    }

    @Test
    public void testUpdateThrowsException(){
        doReturn(Optional.empty()).when(repository).findById(22L);
         assertThrows(ResponseStatusException.class, () -> {
         service.update(22L, burger, owner);
         });
    }

    @Test
    public void testSave(){
        doReturn(burger).when(repository).save(burger);

        MealEntry actual = service.save(burger, owner);

        assertEquals("test@test.de", actual.getOwner());
    }

    @Test
    public void testDelete(){
        service.delete(1L, owner);
        verify(repository).deleteById(1L);
    }
}
